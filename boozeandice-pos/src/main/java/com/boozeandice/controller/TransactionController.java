package com.boozeandice.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boozeandice.entity.Transaction;
import com.boozeandice.service.TransactionService;

@Controller
@RequestMapping(path="/transaction")
public class TransactionController {
	
	private static final Logger logger = LogManager.getLogger(TransactionController.class);
	
	@Autowired
	private PageController pageController;
	
	@Autowired
	private TransactionService transactionService; 
	
	@GetMapping(path="")
	public String transaction(Model model) {
		List<Transaction> transactionList = transactionService.getAll();
		model.addAttribute("transactionList", transactionList);
		return pageController.transactionPage(model);
	}

}
