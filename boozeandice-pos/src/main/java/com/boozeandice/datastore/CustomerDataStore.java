package com.boozeandice.datastore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boozeandice.entity.Customer;
import com.boozeandice.repository.CustomerRepository;

import jakarta.annotation.PostConstruct;

@Component
public class CustomerDataStore {
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@PostConstruct
	private void init() {
		
		Customer customer = new Customer();
		customer.setFname("Malcom");
		customer.setLname("Bordonada");
		customer.setEmailAddress("malcom.bordonada@gmail.com");
		
		customerRepo.save(customer);
		
		customer = new Customer();
		customer.setFname("Maverick");
		customer.setLname("Bordonada");
		customer.setEmailAddress("maverick.bordonada@gmail.com");
		
		customerRepo.save(customer);
		
		customer = new Customer();
		customer.setFname("Mica");
		customer.setLname("Bordonada");
		customer.setEmailAddress("mica.bordonada@gmail.com");
		
		customerRepo.save(customer);

	}

}
