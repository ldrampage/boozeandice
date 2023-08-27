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

import com.boozeandice.entity.ProductCategory;
import com.boozeandice.repository.ProductCategoryRepository;

@Service
public class CategoryService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LogManager.getLogger(CategoryService.class);
	
	@Autowired
	private ProductCategoryRepository categoryRepo;
	
	public Set<ProductCategory> getAll(){
		return new HashSet<ProductCategory>(categoryRepo.findAll());
	}
	
	public ProductCategory save(ProductCategory pcategory) {
		return categoryRepo.save(pcategory);
	}
	
	public ProductCategory getById(Long id) {
		Optional<ProductCategory> pcOpt = categoryRepo.findById(id);
		if(pcOpt.isPresent()) {
			return pcOpt.get();
		}
		return null;
	}
	
	public ProductCategory getByName(String name) {
		Optional<ProductCategory> pcOpt = categoryRepo.findByNameIgnoreCase(name);
		if(pcOpt.isPresent()) {
			return pcOpt.get();
		}
		return null;
	}

}
