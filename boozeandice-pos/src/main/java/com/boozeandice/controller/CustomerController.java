package com.boozeandice.controller;

import java.util.List;
import java.util.Map;

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

import com.boozeandice.entity.Customer;
import com.boozeandice.service.CustomerService;

@Controller
@RequestMapping(path = "/customer")
@Secured({ "ROLE_ADMIN", "ROLE_SUPERVISOR" })
public class CustomerController {

	private static final Logger logger = LogManager.getLogger(CustomerController.class);

	@Autowired
	private PageController pageController;

	@Autowired
	private CustomerService customerService;

	@GetMapping
	public String customerList(Model model) {
		List<Customer> customerList = customerService.getAll();
		model.addAttribute("customerList", customerList);
		return pageController.customerPage(model);
	}

	@GetMapping(path = "/edit/{customerId}")
	public String customerList(Model model, @PathVariable String customerId) {
		Customer customer = customerService.getById(Long.valueOf(customerId));
		model.addAttribute("customer", customer);
		return pageController.customerEditPage(model);
	}

	@PostMapping(path = "/edit/{customerId}")
	public String customerEditProcess(Model model, @RequestParam Map<String, String> parameters) {
		logger.debug("In customerEditProcess()");
		customerService.customerEditPrep(model, parameters);
		return pageController.customerEditPage(model);
	}

	@GetMapping(path = "/add")
	public String customerAdd(Model model) {
		return pageController.customerAddPage(model);
	}

	@PostMapping(path = "/add")
	public String customerAddProcess(Model model, @RequestParam Map<String, String> parameters) {
		logger.debug("In customerAddProcess() -> ");
		customerService.customerAddPrep(model, parameters);
		return pageController.customerAddPage(model);
	}

}
