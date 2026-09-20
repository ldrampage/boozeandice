package com.boozeandice.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.boozeandice.config.UserDetailsImpl;
import com.boozeandice.entity.Customer;
import com.boozeandice.entity.User;
import com.boozeandice.repository.CustomerRepository;

@Service
public class CustomerService implements Serializable {
	
	private static final long serialVersionUID = 210305542883425966L;

	private static final Logger logger = LogManager.getLogger(CustomerService.class);
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private UserService userService;
	
	public void customerAddPrep(Model model, Map<String, String> parameters) {
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
				this.save(customer);
				message.put("status", "success");			}
		} catch (Exception ex) {
			message.put("status", "error");
			message.put("message", ex.getMessage());
			ex.printStackTrace();
		}

		logger.debug("Out customerAddProcess()");
		model.addAttribute("message", message);
	}
	
	public void customerEditPrep(Model model, Map<String, String> parameters) {

		Map<String, String> message = new HashMap<String, String>();
		Customer customer = null;
		try {
			if(parameters.get("edit_customber_btn") != null) {
				customer = this.getById(Long.valueOf(parameters.get("customerId")));
				customer.setName(parameters.get("name"));
				customer.setPhoneNumber(parameters.get("mobile_number"));
				customer.setEmailAddress(parameters.get("email_address"));
				customer = this.save(customer);
				message.put("status", "success");
			}
		} catch (Exception exp) {
			message.put("status", "error");
			message.put("message", exp.getMessage());
			logger.error(exp.getMessage());

		}
		model.addAttribute("message", message);
		model.addAttribute("customer", customer);
	}
	
	public List<Customer> getAll(){
		return customerRepo.findAll();
	}
	
	public Customer getById(Long customerId) {
		Optional<Customer> optCust =  customerRepo.findById(customerId);
		if(optCust.isPresent()) {
			return optCust.get();
		}
		return null;
	}
	
	public Customer save(Customer customer) {
		return customerRepo.save(customer);
	}
	

}
