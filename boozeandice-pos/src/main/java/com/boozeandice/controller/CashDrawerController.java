package com.boozeandice.controller;

import java.sql.Timestamp;
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
	public String cashdrawerPage(Model model) {
		CashDrawer cashDrawerToday = cashDrawerService.getByToday();
		Set<Transaction> transactionList = transactionService.getByCashDrawer(cashDrawerToday);

		List<Expense> expenseList = expenseService.getByCashDrawerToday(cashDrawerToday);

		if (cashDrawerToday != null) {
			cashDrawerToday.setTransactions(transactionList);

			// Set total cash added
			Double totalCashAdded = 0.0;
			if (cashDrawerToday.getCashAdded() != null && cashDrawerToday.getCashAdded().size() > 0) {
				for (CashAdded ca : cashDrawerToday.getCashAdded()) {
					totalCashAdded = totalCashAdded + ca.getCash();
				}
			}

			// Set total expenses
			Double totalExpenses = 0.0;
			if (cashDrawerToday.getExpenses() != null && cashDrawerToday.getExpenses().size() > 0) {
				for (Expense ca : cashDrawerToday.getExpenses()) {
					totalExpenses = totalExpenses + ca.getExpense();
				}
			}

			logger.debug("transactions: " + cashDrawerToday.getTransactions().size());

			// set total cash sales and total cash in drawer
			if (cashDrawerToday.getTransactions() != null && cashDrawerToday.getTransactions().size() > 0) {
				for (Transaction transaction : cashDrawerToday.getTransactions()) {

					// totalsales
					if (cashDrawerToday.getTotalCashSales() == null)
						cashDrawerToday.setTotalCashSales(0.0);
					cashDrawerToday.setTotalCashSales(cashDrawerToday.getTotalCashSales() + transaction.getTotal());

					// totalcashindrawer cash payment
					if (transaction.getPaymentMethod().equals(PaymentMethod.CASH.getDescription())) {
						if (cashDrawerToday.getTotalCashInDrawer() == null) {
							cashDrawerToday.setTotalCashInDrawer(0.0);
						}
						cashDrawerToday
								.setTotalCashInDrawer(cashDrawerToday.getTotalCashInDrawer() + transaction.getTotal());
					}
					// totalgcashpayment
					if (transaction.getPaymentMethod().equals(PaymentMethod.GCASH.getDescription())) {
						if (cashDrawerToday.getTotalGCashPayments() == null)
							cashDrawerToday.setTotalGCashPayments(0.0);
						cashDrawerToday.setTotalGCashPayments(
								cashDrawerToday.getTotalGCashPayments() + transaction.getTotal());
					}

					// total credit card payments
					if (transaction.getPaymentMethod().equals(PaymentMethod.PAYMAYA.getDescription())) {
						if (cashDrawerToday.getTotalCreditCardPayments() == null)
							cashDrawerToday.setTotalCreditCardPayments(0.0);
						cashDrawerToday.setTotalCreditCardPayments(
								cashDrawerToday.getTotalCreditCardPayments() + transaction.getTotal());
					}

				}
				logger.debug("totalCashSales: " + cashDrawerToday.getTotalCashSales());
				logger.debug("totalCashSales: " + cashDrawerToday.getTotalCashInDrawer());

			} else {
				cashDrawerToday.setTotalCashSales(0.0);
				cashDrawerToday.setTotalCashInDrawer(0.0);
			}

			// totalCashInDrawer + totalCashAdded + startingcash - Expenses
			Double totalCashInDrawer = cashDrawerToday.getTotalCashInDrawer() + totalCashAdded
					+ cashDrawerToday.getStartingCash() - totalExpenses;

			cashDrawerToday.setTotalCashAdded(totalCashAdded);
			cashDrawerToday.setTotalExpenses(totalExpenses);
			cashDrawerToday.setTotalCashInDrawer(totalCashInDrawer);
			
			
			//Setting the output in UI
			cashDrawerToday.setStartingCash(Double.valueOf(String.format("%.2f", cashDrawerToday.getStartingCash())));
			cashDrawerToday.setTotalCashAdded(Double.valueOf(String.format("%.2f", cashDrawerToday.getTotalCashAdded())));
			cashDrawerToday.setTotalExpenses(Double.valueOf(String.format("%.2f", cashDrawerToday.getTotalExpenses())));
			cashDrawerToday.setTotalCashSales(Double.valueOf(String.format("%.2f", cashDrawerToday.getTotalCashSales())));
			cashDrawerToday.setTotalCashInDrawer(Double.valueOf(String.format("%.2f", cashDrawerToday.getTotalCashInDrawer())));
			if(cashDrawerToday.getTotalGCashPayments() != null)
				cashDrawerToday.setTotalGCashPayments(Double.valueOf(String.format("%.2f", cashDrawerToday.getTotalGCashPayments())));
			if(cashDrawerToday.getTotalCreditCardPayments() != null)
				cashDrawerToday.setTotalCreditCardPayments(Double.valueOf(String.format("%.2f", cashDrawerToday.getTotalCreditCardPayments())));
			
			model.addAttribute("cashDrawerToday", cashDrawerToday);
		}

		if (expenseList != null && expenseList.size() > 0) {
			model.addAttribute("expenseList", expenseList);
		}

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

		return pageController.cashdrawerPage(model);
	}
}
