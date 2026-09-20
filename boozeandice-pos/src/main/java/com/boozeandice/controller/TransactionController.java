package com.boozeandice.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boozeandice.entity.Address;
import com.boozeandice.entity.Shipment;
import com.boozeandice.entity.Transaction;
import com.boozeandice.entity.User;
import com.boozeandice.repository.AddressRepository;
import com.boozeandice.repository.ShipmentRepository;
import com.boozeandice.service.TransactionService;
import com.boozeandice.service.UserService;

@Controller
@RequestMapping(path="/transaction")
@Secured({"ROLE_ADMIN","ROLE_SUPERVISOR", "ROLE_CASHIER"})
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
		transactionService.transactionViewPage(model, id);
		return pageController.transactionViewPage(model);
	}
	
	@PostMapping(path="/view/{id}")
	public String transactionViewShipmentEdit(Model model, @PathVariable("id") String id, @RequestParam Map<String, String> parameters) throws ParseException {
		transactionService.transactionViewShipmentEdit(model, id, parameters);
		return this.transactionViewPage(model,id);
		
	}

}
