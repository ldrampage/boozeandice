package com.boozeandice.datastore;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boozeandice.entity.Product;
import com.boozeandice.entity.User;
import com.boozeandice.repository.ProductRepository;
import com.boozeandice.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Component
public class ProductDataStore implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LogManager.getLogger(ProductDataStore.class);
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@PostConstruct
	public void init() {
		logger.debug("Start inserting product");
		List<Product> productList = new ArrayList<>();
		Product product = null;
		String[] drinks = {"Red Horse 330mL Bottle","Red Horse 640mL Bottle","Red Horse 330mL Can", "Red Horse 500mL Can"};
		Double[] prices = {50.0, 74.0, 44.0, 90.0};
		for(int x = 0; x<drinks.length; x++) {
			product = new Product();
			product.setName(drinks[x]);
			product.setPrice(prices[x]);
			product.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			
			
			product.setCreatedBy(userRepo.findByUsername("lxbordo"));
			
			productList.add(product);
			
		}
		productRepo.saveAll(productList);

	}

}
