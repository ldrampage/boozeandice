package com.boozeandice.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boozeandice.entity.Product;
import com.boozeandice.entity.ProductCategory;
import com.boozeandice.repository.ProductRepository;

@Service
public class ProductService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private ProductRepository productRepo;
	
	public List<Product> getAll(){
		return productRepo.findAll();
	}
	
	public List<Product> getAllNonZeroStock(){
		return productRepo.findByStocksNonZero();
	}
	
	public Product getById(Long productId) {
		Optional<Product> productOpt = productRepo.findById(productId);
		if(productOpt.isPresent()) {
			return productOpt.get();
		}
		return null;
	}
	
	public List<Product> searchByName(String search){
		return productRepo.findByNameIgnoreCaseLike("%" + search + "%");
	}
	
	public List<Product> getProductByCategory(ProductCategory category){
		return productRepo.findByProductCategory(category);
	}
	
	public Product save(Product product) {
		return productRepo.save(product);
	}
	

}
