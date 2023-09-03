package com.boozeandice.config;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.boozeandice.entity.JobPosition;
import com.boozeandice.entity.Product;
import com.boozeandice.entity.ProductCategory;
import com.boozeandice.entity.ProductStock;
import com.boozeandice.entity.Role;
import com.boozeandice.entity.User;
import com.boozeandice.repository.JobPositionRepository;
import com.boozeandice.repository.ProductCategoryRepository;
import com.boozeandice.repository.ProductRepository;
import com.boozeandice.repository.ProductStockRepository;
import com.boozeandice.repository.RoleRepository;
import com.boozeandice.repository.UserRepository;

import jakarta.transaction.Transactional;

@Configuration
public class DataInitializer {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private JobPositionRepository jobPosRepo;
	
	@Autowired
	private ProductCategoryRepository productCatRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private ProductStockRepository productStockRepo;

	
	@Transactional
	@Bean
	public CommandLineRunner initializeData() {
		
		return args -> {
			
			// Create user
			User user = new User();
			user.setFname("Lyndon");
			user.setLname("Bordonada");
			user.setAbout("Application Developer / Full Stack Web Developer / Food Lover / Coffee Lover / Family Guy");
			user.setMobileNumber("+639565776738");
			
			user.setUsername("lxbordo");
			user.setPassword("malcom19");
			user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			userRepo.save(user);
			
			//Create Roles
			Role role = new Role();
			role.setName("SUPERADMIN");
			roleRepo.save(role);
			
			role = new Role();
			role.setName("ADMIN");
			roleRepo.save(role);
			
			role = new Role();
			role.setName("SUPERVISOR");
			roleRepo.save(role);
			
			role = new Role();
			role.setName("CASHIER");
			roleRepo.save(role);
			
			//Create Job Position
			JobPosition jobPosition = new JobPosition();
			
			jobPosition.setName("Store Manager");
			jobPosition.setDescription("Responsible for overseeing the daily operations of the store, managing staff, inventory management, customer service, and ensuring sales goals are met.");
			jobPosRepo.save(jobPosition);
			
			jobPosition = new JobPosition();
			jobPosition.setName("Assistant Manager");
			jobPosition.setDescription("Assists the store manager in various tasks, including inventory management, staff supervision, customer service, and administrative duties.");
			jobPosRepo.save(jobPosition);
			
			jobPosition = new JobPosition();
			jobPosition.setName("Cashier");
			jobPosition.setDescription("Handles customer transactions, operates the cash register, and provides excellent customer service.");
			jobPosRepo.save(jobPosition);
			
			jobPosition = new JobPosition();
			jobPosition.setName("Sales Associate");
			jobPosition.setDescription("Assists customers in finding products, restocks shelves, and provides product recommendations.");
			jobPosRepo.save(jobPosition);
			
			jobPosition = new JobPosition();
			jobPosition.setName("Inventory Clerk");
			jobPosition.setDescription("Manages inventory levels, restocks products, receives shipments, and conducts regular inventory checks.");
			jobPosRepo.save(jobPosition);
			
			jobPosition = new JobPosition();
			jobPosition.setName("Security Personnel");
			jobPosition.setDescription("Monitors the store for theft and ensures the safety of both customers and staff.");
			jobPosRepo.save(jobPosition);
			
			jobPosition = new JobPosition();
			jobPosition.setName("Delivery Driver");
			jobPosition.setDescription("Responsible for delivering orders to customers' homes or businesses, if the store offers delivery services.");
			jobPosRepo.save(jobPosition);
			
			jobPosition = new JobPosition();
			jobPosition.setName("Bartender");
			jobPosition.setDescription("Prepares and serves alcoholic and non-alcoholic beverages, takes customer orders, interacts with patrons, and maintains the bar area.");
			jobPosRepo.save(jobPosition);
			
			jobPosition = new JobPosition();
			jobPosition.setName("Bar Manager");
			jobPosition.setDescription("Oversees the bar's operations, manages bartenders and staff, ensures compliance with alcohol regulations, and maintains inventory.");
			jobPosRepo.save(jobPosition);
			
			jobPosition = new JobPosition();
			jobPosition.setName("Server");
			jobPosition.setDescription("Takes orders, serves drinks and food, and provides a positive dining experience to customers.");
			jobPosRepo.save(jobPosition);
			
			jobPosition = new JobPosition();
			jobPosition.setName("Barback");
			jobPosition.setDescription("Assists the bartender by restocking supplies, cleaning glasses, clearing tables, and ensuring the bar area is organized.");
			jobPosRepo.save(jobPosition);
			
			jobPosition = new JobPosition();
			jobPosition.setName("Waitstaff");
			jobPosition.setDescription("Takes orders, serves customers, and ensures a high level of customer satisfaction in the dining area.");
			jobPosRepo.save(jobPosition);
			
			//Create Product Category
			List<ProductCategory> productCategoryList = new ArrayList<>();
			ProductCategory category = null;
			User user1 = userRepo.findByUsername("lxbordo");
			String[] drinks = { "Beer", "Silog Meals"};
			for (int x = 0; x < drinks.length; x++) {
				category = new ProductCategory();
				category.setName(drinks[x]);
				category.setUser(user1);
				
				productCatRepo.save(category);
				productCategoryList.add(category);

			}
			
			// Create Product
			logger.debug("Start inserting product");
			List<Product> productList = new ArrayList<>();
			Product product = null;
			String[] products = {"Red Horse 330mL Bottle",
					"Red Horse 640mL Bottle",
					"Red Horse 330mL Can", 
					"Red Horse 500mL Can",};
			
			ProductCategory[]  category1 = {productCatRepo.findById(Long.valueOf(1)).orElseThrow(), 
					productCatRepo.findById(Long.valueOf(1)).orElseThrow(),
					productCatRepo.findById(Long.valueOf(1)).orElseThrow(),
					productCatRepo.findById(Long.valueOf(1)).orElseThrow()};
			
			Double[] prices = {50.0, 74.0, 44.0, 90.0}; 
			Double[] cost = {35.0,45.0,22.0,40.0};
			Double[] packagingFee = {5.0,5.0,4.0,3.0};

			for(int x = 0; x<products.length; x++) {
				product = new Product();
				product.setName(products[x]);
				product.setPrice(prices[x]);
				product.setProductCategory(category1[x]);
				product.setCreatedDate(new Timestamp(System.currentTimeMillis()));
				product.setCost(cost[x]);
				product.setPackagingFee(packagingFee[x]);
				product.setImgLocation("beer.png");
				product.setCreatedBy(user1);
				
				productRepo.save(product);

				productList.add(product);
				
			}
			//productRepo.saveAll(productList);
			
			
			// Create Product Stock
			ProductStock productStock = new ProductStock();
			
			Optional<Product> product1 = productRepo.findById(Long.valueOf(1));
			
			productStock.setProduct(product1.get());
			productStock.setCost(Double.valueOf(2745));
			productStock.setPurchaseDate(new Timestamp(System.currentTimeMillis()));
			productStock.setCreatedBy(user);
			productStock.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			productStock.setQuantity(Long.valueOf(100));
			
			Long newStockBatch = productStock.getQuantity();
			product1.get().setStocks(product1.get().getStocks() + newStockBatch);
			
			productStockRepo.save(productStock);
			productRepo.save(product1.get());
			
			productStock = new ProductStock();
			product1 = productRepo.findById(Long.valueOf(2));
			productStock.setProduct(product1.get());
			productStock.setCost(Double.valueOf(2000));
			productStock.setPurchaseDate(new Timestamp(System.currentTimeMillis()));
			productStock.setCreatedBy(user);
			productStock.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			productStock.setQuantity(Long.valueOf(80));
			
			newStockBatch = productStock.getQuantity();
			product1.get().setStocks(product1.get().getStocks() + newStockBatch);
			
			productStockRepo.save(productStock);
			productRepo.save(product1.get());

			productStock = new ProductStock();
			product1 = productRepo.findById(Long.valueOf(3));
			productStock.setProduct(product1.get());
			productStock.setCost(Double.valueOf(1894));
			productStock.setPurchaseDate(new Timestamp(System.currentTimeMillis()));
			productStock.setCreatedBy(user);
			productStock.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			productStock.setQuantity(Long.valueOf(74));
			
			newStockBatch = productStock.getQuantity();
			product1.get().setStocks(product1.get().getStocks() + newStockBatch);
			
			productStockRepo.save(productStock);
			productRepo.save(product1.get());

			
			productStock = new ProductStock();
			product1 = productRepo.findById(Long.valueOf(1));
			productStock.setProduct(product1.get());
			productStock.setCost(Double.valueOf(1894));
			productStock.setPurchaseDate(new Timestamp(System.currentTimeMillis()));
			productStock.setCreatedBy(user);
			productStock.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			productStock.setQuantity(Long.valueOf(50));
			
			newStockBatch = productStock.getQuantity();
			product1.get().setStocks(product1.get().getStocks() + newStockBatch);
			
			productStockRepo.save(productStock);
			productRepo.save(product1.get());
			
			
			//Transaction
			
		};
		
		
	}

	
}
