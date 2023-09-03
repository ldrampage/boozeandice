//package com.boozeandice.datastore;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.boozeandice.entity.JobPosition;
//import com.boozeandice.repository.JobPositionRepository;
//
//import jakarta.annotation.PostConstruct;
//
//@Component
//public class JobPositionDataStore {
//	
//	@Autowired
//	private JobPositionRepository jobPosRepo;
//	
//	@PostConstruct
//	private void init() {
//		
//		JobPosition jobPosition = new JobPosition();
//		
//		jobPosition.setName("Store Manager");
//		jobPosition.setDescription("Responsible for overseeing the daily operations of the store, managing staff, inventory management, customer service, and ensuring sales goals are met.");
//		jobPosRepo.save(jobPosition);
//		
//		jobPosition = new JobPosition();
//		jobPosition.setName("Assistant Manager");
//		jobPosition.setDescription("Assists the store manager in various tasks, including inventory management, staff supervision, customer service, and administrative duties.");
//		jobPosRepo.save(jobPosition);
//		
//		jobPosition = new JobPosition();
//		jobPosition.setName("Cashier");
//		jobPosition.setDescription("Handles customer transactions, operates the cash register, and provides excellent customer service.");
//		jobPosRepo.save(jobPosition);
//		
//		jobPosition = new JobPosition();
//		jobPosition.setName("Sales Associate");
//		jobPosition.setDescription("Assists customers in finding products, restocks shelves, and provides product recommendations.");
//		jobPosRepo.save(jobPosition);
//		
//		jobPosition = new JobPosition();
//		jobPosition.setName("Inventory Clerk");
//		jobPosition.setDescription("Manages inventory levels, restocks products, receives shipments, and conducts regular inventory checks.");
//		jobPosRepo.save(jobPosition);
//		
//		jobPosition = new JobPosition();
//		jobPosition.setName("Security Personnel");
//		jobPosition.setDescription("Monitors the store for theft and ensures the safety of both customers and staff.");
//		jobPosRepo.save(jobPosition);
//		
//		jobPosition = new JobPosition();
//		jobPosition.setName("Delivery Driver");
//		jobPosition.setDescription("Responsible for delivering orders to customers' homes or businesses, if the store offers delivery services.");
//		jobPosRepo.save(jobPosition);
//		
//		jobPosition = new JobPosition();
//		jobPosition.setName("Bartender");
//		jobPosition.setDescription("Prepares and serves alcoholic and non-alcoholic beverages, takes customer orders, interacts with patrons, and maintains the bar area.");
//		jobPosRepo.save(jobPosition);
//		
//		jobPosition = new JobPosition();
//		jobPosition.setName("Bar Manager");
//		jobPosition.setDescription("Oversees the bar's operations, manages bartenders and staff, ensures compliance with alcohol regulations, and maintains inventory.");
//		jobPosRepo.save(jobPosition);
//		
//		jobPosition = new JobPosition();
//		jobPosition.setName("Server");
//		jobPosition.setDescription("Takes orders, serves drinks and food, and provides a positive dining experience to customers.");
//		jobPosRepo.save(jobPosition);
//		
//		jobPosition = new JobPosition();
//		jobPosition.setName("Barback");
//		jobPosition.setDescription("Assists the bartender by restocking supplies, cleaning glasses, clearing tables, and ensuring the bar area is organized.");
//		jobPosRepo.save(jobPosition);
//		
//		jobPosition = new JobPosition();
//		jobPosition.setName("Waitstaff");
//		jobPosition.setDescription("Takes orders, serves customers, and ensures a high level of customer satisfaction in the dining area.");
//		jobPosRepo.save(jobPosition);
//		
//	}
//
//}
