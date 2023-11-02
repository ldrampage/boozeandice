package com.boozeandice.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boozeandice.config.UserDetailsImpl;
import com.boozeandice.entity.CashAdded;
import com.boozeandice.entity.CashDrawer;
import com.boozeandice.entity.Expense;
import com.boozeandice.entity.Transaction;
import com.boozeandice.entity.User;
import com.boozeandice.enums.PaymentMethod;
import com.boozeandice.service.CashAddedService;
import com.boozeandice.service.CashDrawerService;
import com.boozeandice.service.ExpenseService;
import com.boozeandice.service.TransactionService;
import com.boozeandice.service.UserService;

@Controller
@RequestMapping(path = "/cashdrawer")
@Secured({"ROLE_ADMIN","ROLE_SUPERVISOR","ROLE_CASHIER"})
public class CashDrawerController {

	private static final Logger logger = LogManager.getLogger(CashDrawerController.class);

	private final CashDrawerService cashDrawerService;
	private final TransactionService transactionService;
	private final ExpenseService expenseService;
	private final CashAddedService cashAddedService;
	private final PageController pageController;
	private final UserService userService;
	
	public CashDrawerController(CashDrawerService cashDrawerService, TransactionService transactionService, ExpenseService expenseService,
			CashAddedService cashAddedService, PageController pageController, UserService userService) {
		this.cashDrawerService = cashDrawerService;
		this.transactionService = transactionService;
		this.expenseService = expenseService;
		this.cashAddedService = cashAddedService;
		this.pageController = pageController;
		this.userService = userService;
	}
	

	@GetMapping(path = "")
	public String cashdrawerPage(Model model, String date) {
		CashDrawer cashDrawer = cashDrawerService.getByToday();
		boolean customDate = false;
		if(date != null && date.length() > 0) {
			logger.debug("CashDrawerCustomDate: " + date);
			try {
				Date cashDrawerDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
				cashDrawer = cashDrawerService.getByCreatedDate(cashDrawerDate);
				customDate = true;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		return this.pageController.cashdrawerPage(model);
	}

	@GetMapping(path = "/create")
	public String cashDrawerCreatePage(Model model) {
		return this.pageController.cashDrawerCreate(model);
	}

	@PostMapping(path = "")
	public String cashDrawerCreateProcess(Model model, @RequestParam Map<String, String> parameters) {

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
			return cashdrawerPage(model, parameters.get("cashdrawer_date"));
		} 

		return pageController.cashdrawerPage(model);
	}
}
