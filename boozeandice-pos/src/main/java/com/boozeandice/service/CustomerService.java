package com.boozeandice.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boozeandice.local.entity.Customer;
import com.boozeandice.repository.CustomerRepository;

@Service
public class CustomerService {
	
	private static final Logger logger = LogManager.getLogger(CustomerService.class);
	
	@Autowired
	private CustomerRepository customerRepo;
	
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
