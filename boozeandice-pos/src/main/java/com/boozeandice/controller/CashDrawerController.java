package com.boozeandice.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boozeandice.entity.CashAdded;
import com.boozeandice.entity.CashDrawer;
import com.boozeandice.entity.Expense;
import com.boozeandice.entity.Transaction;
import com.boozeandice.service.CashAddedService;
import com.boozeandice.service.CashDrawerService;
import com.boozeandice.service.ExpenseService;
import com.boozeandice.service.TransactionService;
import com.boozeandice.utility.Utilities;

@Controller
@RequestMapping(path = "/cashdrawer")
public class CashDrawerController {

	private static final Logger logger = LogManager.getLogger(CashDrawerController.class);

	@Autowired
	private PageController pageController;

	@Autowired
	private CashDrawerService cashDrawerService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private ExpenseService expenseService;

	@Autowired
	private CashAddedService cashAddedService;
	
	@Autowired
	private Utilities utility;
	
	@GetMapping(path = "")
	private String cashdrawerPage(Model model) {
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
			
			//set total cash sales
			if(cashDrawerToday.getTransactions() != null && cashDrawerToday.getTransactions().size() > 0) {
				for(Transaction transaction : cashDrawerToday.getTransactions()) {
					if(cashDrawerToday.getTotalCashSales() == null )
						cashDrawerToday.setTotalCashSales(0.0);
					cashDrawerToday.setTotalCashSales(cashDrawerToday.getTotalCashSales() + transaction.getTotal());
				}
				logger.debug("totalCashSales: " + cashDrawerToday.getTotalCashSales());
			} else {
				cashDrawerToday.setTotalCashSales(0.0);
			}
			
			Double totalCash = cashDrawerToday.getStartingCash() + totalCashAdded + cashDrawerToday.getTotalCashSales() - totalExpenses;

			cashDrawerToday.setTotalCashAdded(totalCashAdded);
			cashDrawerToday.setTotalExpenses(totalExpenses);
			cashDrawerToday.setTotalCashInDrawer(totalCash);
			
			
			
			model.addAttribute("cashDrawerToday", cashDrawerToday);
		}
		
		if(expenseList != null && expenseList.size() > 0) {
			model.addAttribute("expenseList", expenseList);
		}
		
		return pageController.cashdrawerPage(model);
	}

	@GetMapping(path = "/create")
	private String cashDrawerCreatePage(Model model) {
		return pageController.cashDrawerCreate(model);
	}

	@PostMapping(path = "")
	private String cashDrawerCreateProcess(Model model, @RequestParam Map<String, String> parameters) {
		if (parameters.get("createStartingCash") != null && parameters.get("starting_cash") != null) {
			String startingCash = parameters.get("starting_cash");
			CashDrawer cashDrawer = new CashDrawer();
			cashDrawer.setStartingCash(Double.valueOf(startingCash));
			cashDrawer.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			// cashDrawer.setCreatedBy(); TODO
			cashDrawerService.save(cashDrawer);
			utility.openCashDrawer();
			return "redirect:/cashdrawer";
		}
		
		if(parameters.get("add_expense") != null) {
			Double expenseAmount = Double.valueOf(parameters.get("expense_amount"));
			String expenseReason = parameters.get("expense_reason");
			logger.debug("expneseAmount: " + expenseAmount + ", expenseReason: " + expenseReason );
			
			Expense expense = new Expense();
			expense.setCashdrawer(cashDrawerService.getByToday());
			expense.setCreatedBy(null); // TODO when login functionality is setup
			expense.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			expense.setExpense(expenseAmount);
			expense.setNote(expenseReason);
			
			expenseService.save(expense);
			return "redirect:/cashdrawer";
		}
		
		if(parameters.get("add_cash_btn") != null) {
			CashAdded cashAdded = new CashAdded();
			cashAdded.setCash(Double.valueOf(parameters.get("add_cash")));
			cashAdded.setCashdrawer(cashDrawerService.getByToday());
			cashAdded.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			cashAdded.setUser(null); // TODO when login functionality is setup
			
			cashAddedService.save(cashAdded);
			return "redirect:/cashdrawer";
		}

		return pageController.cashdrawerPage(model);
	}
}
