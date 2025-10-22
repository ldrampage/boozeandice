package com.boozeandice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boozeandice.config.UserDetailsImpl;
import com.boozeandice.entity.Customer;
import com.boozeandice.entity.User;
import com.boozeandice.service.CustomerService;
import com.boozeandice.service.UserService;

@Controller
@RequestMapping(path = "/customer")
@Secured({ "ROLE_ADMIN", "ROLE_SUPERVISOR" })
public class CustomerController {

	private static final Logger logger = LogManager.getLogger(CustomerController.class);

	@Autowired
	private PageController pageController;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private UserService userService;

	@GetMapping(path = "")
	public String customerList(Model model) {
		List<Customer> customerList = customerService.getAll();
		model.addAttribute("customerList", customerList);
		return pageController.customerPage(model);
	}

	@GetMapping(path = "/edit/{customerId}")
	public String customerList(Model model, @PathVariable("customerId") String customerId) {
		Customer customer = customerService.getById(Long.valueOf(customerId));
		model.addAttribute("customer", customer);
		return pageController.customerEditPage(model);
	}

	@PostMapping(path = "/edit/{customerId}")
	public String customerEditProcess(Model model, @RequestParam Map<String, String> parameters) {
		logger.debug("In customerEditProcess()");
		
		Map<String, String> message = new HashMap<String, String>();
		Customer customer = null;
		try {
			if(parameters.get("edit_customber_btn") != null) {
				customer = customerService.getById(Long.valueOf(parameters.get("customerId")));
				customer.setName(parameters.get("name"));
				customer.setPhoneNumber(parameters.get("mobile_number"));
				customer.setEmailAddress(parameters.get("email_address"));
				customer = customerService.save(customer);
				message.put("status", "success");
			}
		} catch (Exception exp) {
			message.put("status", "error");
			message.put("message", exp.getMessage());
			logger.error(exp.getMessage());

		}
		model.addAttribute("message", message);
		model.addAttribute("customer", customer);
		return pageController.customerEditPage(model);
	}

	@GetMapping(path = "/add")
	public String customerAdd(Model model) {
		return pageController.customerAddPage(model);
	}

	@PostMapping(path = "/add")
	public String customerAddProcess(Model model, @RequestParam Map<String, String> parameters) {
		logger.debug("In customerAddProcess() -> ");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		User user = userService.getByUsername(userDetails.getUsername());
		for (Map.Entry<String, String> param : parameters.entrySet()) {
			logger.debug(param.getKey() + ": " + param.getValue());
		}
		Map<String, String> message = new HashMap<String,String>();
		try {
			if (parameters.get("create_customer_btn") != null) {
				Customer customer = new Customer(user);
				customer.setName(parameters.get("name"));
				customer.setEmailAddress(parameters.get("email_address"));
				customer.setPhoneNumber(parameters.get("mobile_number"));
				customerService.save(customer);
				message.put("status", "success");			}
		} catch (Exception ex) {
			message.put("status", "error");
			message.put("message", ex.getMessage());
			ex.printStackTrace();
		}

		logger.debug("Out customerAddProcess()");
		model.addAttribute("message", message);
		return pageController.customerAddPage(model);
	}

}
