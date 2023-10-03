package com.boozeandice.service;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boozeandice.local.entity.Product;
import com.boozeandice.local.entity.ProductCategory;
import com.boozeandice.local.entity.ProductStock;
import com.boozeandice.local.repository.ProductRepository;
import com.boozeandice.local.repository.ProductStockRepository;
import com.boozeandice.local.repository.TransactionItemRepository;
import com.boozeandice.local.repository.TransactionRepository;
import com.bozeandice.vo.ProductBestSellerVO;

@Service
public class ProductService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private ProductStockRepository productStockRepo;
	
	@Autowired
	private TransactionItemRepository transactionItemRepo;
	
	public Set<Product> getAll(){
		return new HashSet<Product>(productRepo.findAll());
	}
	
	
	public Set<Product> getAllNonZeroStock(){
		return productRepo.findByStocksNonZero();
	}
	
	public Product getById(Long productId) {
		Optional<Product> productOpt = productRepo.findById(productId);
		if(productOpt.isPresent()) {
			return productOpt.get();
		}
		return null;
	}
	
	public Set<Product> searchByName(String search){
		return productRepo.findByNameIgnoreCaseLike("%" + search + "%");
	}
	
	public Set<Product> getProductByCategory(ProductCategory category){
		return productRepo.findByProductCategory(category);
	}
		
	public Set<Product> getByProductCategoryAndProductStockGreaterThan(ProductCategory category){
		return productRepo.findByProductCategoryAndStocksGreaterThan(category, Long.valueOf(0));
	}
	
	public Set<Product> getByNameContainingAndStocksGreaterThan(String productName){
		return productRepo.findByNameContainingAndStocksGreaterThan(productName, Long.valueOf(0));
	}
	
	public Product save(Product product) {
		return productRepo.save(product);
	}
	

}
