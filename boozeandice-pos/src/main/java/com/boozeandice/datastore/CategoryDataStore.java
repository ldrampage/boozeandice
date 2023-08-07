package com.boozeandice.datastore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boozeandice.entity.ProductCategory;
import com.boozeandice.repository.ProductCategoryRepository;

import jakarta.annotation.PostConstruct;

@Component
public class CategoryDataStore implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LogManager.getLogger(CategoryDataStore.class);
	
	@Autowired
	private ProductCategoryRepository productCatRepo;
	
	@PostConstruct
	public void init() {
		logger.debug("Start inserting product categories");
		List<ProductCategory> productCategoryList = new ArrayList<>();
		ProductCategory category = null;
		String[] drinks = {"beer","wine"};
		for(int x = 0; x<drinks.length; x++) {
			category = new ProductCategory();
			category.setName(drinks[x]);
			productCategoryList.add(category);
			
		}
		productCatRepo.saveAll(productCategoryList);

	}

}
