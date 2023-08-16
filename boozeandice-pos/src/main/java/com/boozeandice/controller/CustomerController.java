package com.boozeandice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boozeandice.entity.Customer;
import com.boozeandice.service.CustomerService;


@Controller
@RequestMapping(path="/customer")
public class CustomerController {
	
	private static final Logger logger = LogManager.getLogger(CustomerController.class);
	
	@Autowired
	private PageController pageController;
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping(path="")
	public String customerList(Model model) {
		List<Customer> customerList = customerService.getAll();
		model.addAttribute("customerList", customerList);
		return pageController.customerPage(model);
	}
	
	@GetMapping(path="/edit/{customerId}")
	public String customerList(Model model, @PathVariable("customerId") String customerId) {
		Customer customer = customerService.getById(Long.valueOf(customerId));
		model.addAttribute("customer", customer);
		return pageController.customerEditPage(model);
	}	
	
	@PostMapping(path="/edit/{customerId}")
	public String customerEdit(Model model, @RequestParam Map<String, String> parameters) {
		Customer customer = customerService.getById(Long.valueOf(parameters.get("customerId")));
		customer.setFname(parameters.get("first_name"));
		customer.setLname(parameters.get("last_name"));
		customer.setEmailAddress(parameters.get("email_address"));
		Map<String, String> message = new HashMap<String, String>();
		try {
			customer = customerService.save(customer);
			message.put("status", "success");
		} catch(Exception exp) {
			message.put("status", "error");
			message.put("message", exp.getMessage());
			logger.error(exp.getMessage());
			
		}
		model.addAttribute("message",message);
		model.addAttribute("customer", customer);
		return pageController.customerEditPage(model);
	}
	
}
