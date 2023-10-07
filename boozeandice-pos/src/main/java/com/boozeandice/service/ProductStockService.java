package com.boozeandice.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boozeandice.local.entity.Product;
import com.boozeandice.local.entity.ProductStock;
import com.boozeandice.repository.ProductStockRepository;

@Service
public class ProductStockService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LogManager.getLogger(ProductStockService.class);
	
	@Autowired
	private ProductStockRepository productStockRepo;
	
	public ProductStock getByBarcodeDigits(Long barcodeDigits) {
		return productStockRepo.findByBarcodeDigits(barcodeDigits);
	}
	
	public List<ProductStock> getAll(){
		return productStockRepo.findAll();
	} 
	
	public ProductStock getById(Long productStockId) {
		Optional<ProductStock> psOpt = productStockRepo.findById(productStockId);
		if(psOpt.isPresent()) {
			return psOpt.get();
		}
		return null;
	}
	
	public List<ProductStock> getByProduct(Product product) {
		return productStockRepo.findByProduct(product);
	}
	
	
	public ProductStock save(ProductStock productStock) {
		return productStockRepo.save(productStock);
	}

}
