package com.boozeandice.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.boozeandice.config.UserDetailsImpl;
import com.boozeandice.entity.CashAdded;
import com.boozeandice.entity.CashDrawer;
import com.boozeandice.entity.Expense;
import com.boozeandice.entity.Transaction;
import com.boozeandice.entity.User;
import com.boozeandice.enums.PaymentMethod;
import com.boozeandice.exceptions.InvalidDateFormatException;
import com.boozeandice.repository.CashDrawerRepository;

@Service
public class CashDrawerService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LogManager.getLogger(CashDrawerService.class);
	
	@Autowired
	private CashDrawerRepository cashDrawerRepo;	
	
	@Autowired
	private CashDrawerService cashDrawerService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CashAddedService cashAddedService;
	
	@Transactional(propagation= Propagation.REQUIRED)
	public String cashDrawerCreatePrepare(Model model, Map<String, String> parameters) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		User user = userService.getByUsername(userDetails.getUsername());

		if (parameters.get("createStartingCash") != null && parameters.get("starting_cash") != null) {
			String startingCash = parameters.get("starting_cash");
			CashDrawer cashDrawer = new CashDrawer();
			cashDrawer.setStartingCash(Double.valueOf(startingCash));
			cashDrawer.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			cashDrawer.setCreatedBy(user);
			cashDrawerService.save(cashDrawer);
			//utility.openCashDrawer();
			return "redirect:/cashdrawer";
		}

		if (parameters.get("add_expense") != null) {
			Double expenseAmount = Double.valueOf(parameters.get("expense_amount"));
			String expenseReason = parameters.get("expense_reason");
			logger.debug("expneseAmount: " + expenseAmount + ", expenseReason: " + expenseReason);

			Expense expense = new Expense();
			expense.setCashdrawer(cashDrawerService.getByToday());
			expense.setCreatedBy(user); 
			expense.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			expense.setExpense(expenseAmount);
			expense.setNote(expenseReason);

			expenseService.save(expense);
			return "redirect:/cashdrawer";
		}

		if (parameters.get("add_cash_btn") != null) {
			CashAdded cashAdded = new CashAdded();
			cashAdded.setCash(Double.valueOf(parameters.get("add_cash")));
			cashAdded.setCashdrawer(cashDrawerService.getByToday());
			cashAdded.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			cashAdded.setUser(user);

			cashAddedService.save(cashAdded);
			return "redirect:/cashdrawer";
		}
		
		if(parameters.get("cashdrawerdate_date_submit") != null) {
			this.cashDrawerPagePrepare(model, parameters.get("cashdrawer_date"));
			return null;
		}
		return null;
	}
	
	public void cashDrawerPagePrepare(Model model, String date) {
		CashDrawer cashDrawer = cashDrawerService.getByToday();
		boolean customDate = false;
		if(date != null && date.length() > 0) {
			logger.debug("CashDrawerCustomDate: " + date);
			try {
				Date cashDrawerDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
				cashDrawer = cashDrawerService.getByCreatedDate(cashDrawerDate);
				customDate = true;
			} catch (ParseException e) {
				throw new InvalidDateFormatException(e.getMessage(), e.getCause());
			}
			
			
		}
		Set<Transaction> transactionList = transactionService.getByCashDrawer(cashDrawer);

		List<Expense> expenseList = expenseService.getByCashDrawerToday(cashDrawer);

		if (cashDrawer != null) {
			cashDrawer.setTransactions(transactionList);

			// Set total cash added
			Double totalCashAdded = 0.0;
			if (cashDrawer.getCashAdded() != null && cashDrawer.getCashAdded().size() > 0) {
				for (CashAdded ca : cashDrawer.getCashAdded()) {
					totalCashAdded = totalCashAdded + ca.getCash();
				}
			}

			// Set total expenses
			Double totalExpenses = 0.0;
			if (cashDrawer.getExpenses() != null && cashDrawer.getExpenses().size() > 0) {
				for (Expense ca : cashDrawer.getExpenses()) {
					totalExpenses = totalExpenses + ca.getExpense();
				}
			}

			logger.debug("transactions: " + cashDrawer.getTransactions().size());

			// set total cash sales and total cash in drawer
			if (cashDrawer.getTransactions() != null && cashDrawer.getTransactions().size() > 0) {
				for (Transaction transaction : cashDrawer.getTransactions()) {

					// totalsales
					if (cashDrawer.getTotalCashSales() == null)
						cashDrawer.setTotalCashSales(0.0);
					cashDrawer.setTotalCashSales(cashDrawer.getTotalCashSales() + transaction.getTotal());

					// totalcashindrawer cash payment
					if (transaction.getPaymentMethod().equals(PaymentMethod.CASH.getDescription())) {
						if (cashDrawer.getTotalCashInDrawer() == null) {
							cashDrawer.setTotalCashInDrawer(0.0);
						}
						cashDrawer
								.setTotalCashInDrawer(cashDrawer.getTotalCashInDrawer() + transaction.getTotal());
					}
					// totalgcashpayment
					if (transaction.getPaymentMethod().equals(PaymentMethod.GCASH.getDescription())) {
						if (cashDrawer.getTotalGCashPayments() == null)
							cashDrawer.setTotalGCashPayments(0.0);
						cashDrawer.setTotalGCashPayments(
								cashDrawer.getTotalGCashPayments() + transaction.getTotal());
					}

					// total credit card payments
					if (transaction.getPaymentMethod().equals(PaymentMethod.PAYMAYA.getDescription())) {
						if (cashDrawer.getTotalCreditCardPayments() == null)
							cashDrawer.setTotalCreditCardPayments(0.0);
						cashDrawer.setTotalCreditCardPayments(
								cashDrawer.getTotalCreditCardPayments() + transaction.getTotal());
					}

				}
				logger.debug("totalCashSales: " + cashDrawer.getTotalCashSales());
				logger.debug("totalCashSales: " + cashDrawer.getTotalCashInDrawer());

			} else {
				cashDrawer.setTotalCashSales(0.0);
				cashDrawer.setTotalCashInDrawer(0.0);
			}

			// totalCashInDrawer + totalCashAdded + startingcash - Expenses
			Double totalCashInDrawer = cashDrawer.getTotalCashInDrawer() + totalCashAdded
					+ cashDrawer.getStartingCash() - totalExpenses;

			cashDrawer.setTotalCashAdded(totalCashAdded);
			cashDrawer.setTotalExpenses(totalExpenses);
			cashDrawer.setTotalCashInDrawer(totalCashInDrawer);
			
			
			//Setting the output in UI
			cashDrawer.setStartingCash(Double.valueOf(String.format("%.2f", cashDrawer.getStartingCash())));
			cashDrawer.setTotalCashAdded(Double.valueOf(String.format("%.2f", cashDrawer.getTotalCashAdded())));
			cashDrawer.setTotalExpenses(Double.valueOf(String.format("%.2f", cashDrawer.getTotalExpenses())));
			cashDrawer.setTotalCashSales(Double.valueOf(String.format("%.2f", cashDrawer.getTotalCashSales())));
			cashDrawer.setTotalCashInDrawer(Double.valueOf(String.format("%.2f", cashDrawer.getTotalCashInDrawer())));
			if(cashDrawer.getTotalGCashPayments() != null)
				cashDrawer.setTotalGCashPayments(Double.valueOf(String.format("%.2f", cashDrawer.getTotalGCashPayments())));
			if(cashDrawer.getTotalCreditCardPayments() != null)
				cashDrawer.setTotalCreditCardPayments(Double.valueOf(String.format("%.2f", cashDrawer.getTotalCreditCardPayments())));
			
			model.addAttribute("cashDrawerToday", cashDrawer);
		}

		if (expenseList != null && expenseList.size() > 0) {
			model.addAttribute("expenseList", expenseList);
		}
		model.addAttribute("cashDrawerDate", date);
		model.addAttribute("customDate", customDate);
	}
	
	public CashDrawer getByToday() {
		Calendar startOfDay = Calendar.getInstance();
        startOfDay.setTime(new Date());
        startOfDay.set(Calendar.HOUR_OF_DAY, 0);
        startOfDay.set(Calendar.MINUTE, 0);
        startOfDay.set(Calendar.SECOND, 0);
        startOfDay.set(Calendar.MILLISECOND, 0);

        Calendar endOfDay = Calendar.getInstance();
        endOfDay.setTime(new Date()); 
        endOfDay.set(Calendar.HOUR_OF_DAY, 23);
        endOfDay.set(Calendar.MINUTE, 59);
        endOfDay.set(Calendar.SECOND, 59);
        endOfDay.set(Calendar.MILLISECOND, 999);
		return cashDrawerRepo.findByCreatedDateBetween(startOfDay.getTime(), endOfDay.getTime());
	}
	
	public CashDrawer getByCreatedDate(Date date) {
		Calendar start = Calendar.getInstance();
		start.setTime(date);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);
		Calendar end = Calendar.getInstance();
		end.setTime(date);
		end.set(Calendar.HOUR_OF_DAY, 23);
		end.set(Calendar.MINUTE, 59);
		end.set(Calendar.SECOND, 59);
		end.set(Calendar.MILLISECOND, 999);
		return cashDrawerRepo.findByCreatedDateBetween(start.getTime(), end.getTime());
	}
	
	public CashDrawer save(CashDrawer cashDrawer) {
		return cashDrawerRepo.save(cashDrawer);
	}

	
}
