package com.boozeandice.controller;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boozeandice.entity.Transaction;
import com.boozeandice.service.TransactionService;

@Controller
@RequestMapping(path="/transaction")
@Secured({"ROLE_ADMIN","ROLE_SUPERVISOR"})
public class TransactionController {
	
	private static final Logger logger = LogManager.getLogger(TransactionController.class);
	
	@Autowired
	private PageController pageController;
	
	@Autowired
	private TransactionService transactionService; 
	
	@GetMapping(path="")
	public String transaction(Model model) {
		Set<Transaction> transactionList = transactionService.getAll();
		Set<Transaction> transactionListToday = transactionService.getByToday();
		
		model.addAttribute("transactionListToday", transactionListToday);
		model.addAttribute("transactionList", transactionList);
		return pageController.transactionPage(model);
	}
	
	@GetMapping(path="/view/{id}")
	public String transactionViewPage(Model model, @PathVariable("id") String id) {
		Transaction transaction = transactionService.getById(Long.valueOf(id));
		model.addAttribute("transaction", transaction);
		return pageController.transactionViewPage(model);
	}

}
