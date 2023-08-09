package com.boozeandice.datastore;

import java.sql.Timestamp;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boozeandice.entity.Product;
import com.boozeandice.entity.ProductStock;
import com.boozeandice.entity.User;
import com.boozeandice.repository.ProductRepository;
import com.boozeandice.repository.ProductStockRepository;
import com.boozeandice.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Component
public class ProductStockDataStore {
	
	private static final Logger logger = LogManager.getLogger(ProductDataStore.class);
	
	@Autowired
	private ProductStockRepository productStockRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired 
	private UserRepository userRepo;
	
	@PostConstruct
	public void init() {
		
		ProductStock productStock = new ProductStock();
		
		Optional<Product> product = productRepo.findById(Long.valueOf(1));
		User user = userRepo.findByUsername("lxbordo");
		
		productStock.setProduct(product.get());
		productStock.setCost(Double.valueOf(2745));
		productStock.setPurchaseDate(new Timestamp(System.currentTimeMillis()));
		productStock.setCreatedBy(user);
		productStock.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		productStock.setQuantity(Long.valueOf(100));
		
		Long newStockBatch = productStock.getQuantity();
		product.get().setStocks(product.get().getStocks() + newStockBatch);
		
		productStockRepo.save(productStock);
		productRepo.save(product.get());
		
		productStock = new ProductStock();
		product = productRepo.findById(Long.valueOf(2));
		productStock.setProduct(product.get());
		productStock.setCost(Double.valueOf(2000));
		productStock.setPurchaseDate(new Timestamp(System.currentTimeMillis()));
		productStock.setCreatedBy(user);
		productStock.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		productStock.setQuantity(Long.valueOf(80));
		
		newStockBatch = productStock.getQuantity();
		product.get().setStocks(product.get().getStocks() + newStockBatch);
		
		productStockRepo.save(productStock);
		productRepo.save(product.get());

		productStock = new ProductStock();
		product = productRepo.findById(Long.valueOf(3));
		productStock.setProduct(product.get());
		productStock.setCost(Double.valueOf(1894));
		productStock.setPurchaseDate(new Timestamp(System.currentTimeMillis()));
		productStock.setCreatedBy(user);
		productStock.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		productStock.setQuantity(Long.valueOf(74));
		
		newStockBatch = productStock.getQuantity();
		product.get().setStocks(product.get().getStocks() + newStockBatch);
		
		productStockRepo.save(productStock);
		productRepo.save(product.get());

		
		productStock = new ProductStock();
		product = productRepo.findById(Long.valueOf(1));
		productStock.setProduct(product.get());
		productStock.setCost(Double.valueOf(1894));
		productStock.setPurchaseDate(new Timestamp(System.currentTimeMillis()));
		productStock.setCreatedBy(user);
		productStock.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		productStock.setQuantity(Long.valueOf(50));
		
		newStockBatch = productStock.getQuantity();
		product.get().setStocks(product.get().getStocks() + newStockBatch);
		
		productStockRepo.save(productStock);
		productRepo.save(product.get());


	}

}
