//package com.boozeandice.datastore;
//
//import java.io.Serializable;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.boozeandice.entity.Product;
//import com.boozeandice.entity.ProductCategory;
//import com.boozeandice.entity.User;
//import com.boozeandice.repository.ProductCategoryRepository;
//import com.boozeandice.repository.ProductRepository;
//import com.boozeandice.repository.UserRepository;
//
//import jakarta.annotation.PostConstruct;
//
//@Component
//public class ProductDataStore implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//	
//	private static final Logger logger = LogManager.getLogger(ProductDataStore.class);
//	
//	@Autowired
//	private ProductRepository productRepo;
//	
//	@Autowired
//	private UserRepository userRepo;
//	
//	@Autowired
//	private ProductCategoryRepository categoryRepo;
//	
//	@PostConstruct
//	public void init() throws InterruptedException {
//		logger.debug("Start inserting product");
//		List<Product> productList = new ArrayList<>();
//		Product product = null;
//		String[] products = {"Red Horse 330mL Bottle",
//				"Red Horse 640mL Bottle",
//				"Red Horse 330mL Can", 
//				"Red Horse 500mL Can",
//				"San Mig Light 330mL Bottle",
//				"San Mig Light 640mL Bottle",
//				"San Mig Light 330mL Can", 
//				"San Mig Light 500mL Can"};
//		
//		ProductCategory[]  category = {categoryRepo.findById(Long.valueOf(1)).get(), 
//				categoryRepo.findById(Long.valueOf(1)).get(),
//				categoryRepo.findById(Long.valueOf(1)).get(),
//				categoryRepo.findById(Long.valueOf(1)).get(),
//				categoryRepo.findById(Long.valueOf(1)).get(), 
//				categoryRepo.findById(Long.valueOf(1)).get(),
//				categoryRepo.findById(Long.valueOf(1)).get(),
//				categoryRepo.findById(Long.valueOf(1)).get()};
//		
//		Double[] prices = {50.0, 74.0, 44.0, 90.0,50.0, 74.0, 44.0, 90.0}; 
//		Double[] cost = {35.0,45.0,22.0,40.0,35.0,45.0,22.0,40.0};
//		
//		User user = userRepo.findByUsername("lxbordo".trim());
//		//logger.debug(user.getUsername());
//		for(int x = 0; x<products.length; x++) {
//			product = new Product();
//			product.setName(products[x]);
//			product.setPrice(prices[x]);
//			product.setCreatedDate(new Timestamp(System.currentTimeMillis()));
//			product.setCost(cost[x]);
//			product.setImgLocation("beer.png");
//			
//			product.setCreatedBy(user);
//			
//			productList.add(product);
//			
//		}
//		productRepo.saveAll(productList);
//
//	}
//
//}
