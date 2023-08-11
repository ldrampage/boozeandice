package com.boozeandice.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boozeandice.entity.Product;
import com.boozeandice.entity.ProductCategory;
import com.boozeandice.service.CategoryService;
import com.boozeandice.service.ProductService;

import jakarta.annotation.PostConstruct;

@Controller
public class IndexController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger(IndexController.class);
	
	Set<Product> productToPurchaseList =  new HashSet<>();

	@Autowired
	private PageController pageController;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService productCatService;

	@GetMapping(path = "/")
	public String index(Model model) {

		List<Product> productList = productService.getAllNonZeroStock();		
		List<List<Product>> productsDisplay = this.organizeProductsDisplay(productList);
		
		List<ProductCategory> productCategoryList = productCatService.getAll();
		
		productToPurchaseList.clear();

		model.addAttribute("productCategoryList", productCategoryList);
		model.addAttribute("productsDisplay", productsDisplay);
		return pageController.index(model);
	}

	@PostMapping(path="/")
	public String posProcesses(Model model, @RequestParam Map<String, String> parameters) {
		List<Product> productList = productService.getAllNonZeroStock();
		List<List<Product>> productsDisplay = this.organizeProductsDisplay(productList);
		List<ProductCategory> productCategoryList = productCatService.getAll();


		if(parameters.get("purchase") != null) {
			Product product = productService.getById(Long.valueOf(parameters.get("productId")));
			productToPurchaseList.add(product);
		}
		
		if(parameters.get("removePurchase") != null) {
			Product product = productService.getById(Long.valueOf(parameters.get("productId")));
			productToPurchaseList.remove(product);
		}
		
		if(parameters.get("fetchByCategory") != null) {
			if(!parameters.get("categoryId").equalsIgnoreCase("all")) {
				ProductCategory productCategory = productCatService.getById(Long.valueOf(parameters.get("categoryId")));
				productList = productService.getProductByCategory(productCategory);
				productsDisplay = this.organizeProductsDisplay(productList);
			}
		}
		
		model.addAttribute("productCategoryList", productCategoryList);
		model.addAttribute("productToPurchaseList", productToPurchaseList);
		model.addAttribute("productsDisplay", productsDisplay);
		return pageController.index(model);
	}

	public List<List<Product>> organizeProductsDisplay(List<Product> productList) {
		List<List<Product>> productsDisplay = new ArrayList<>();

		int counter = 3;
		int loop = 0;
		int row = 0;
		int locator = 0;
		List<Product> insertToRows = new ArrayList<>();
		for (Product product : productList) {
			if (loop < counter) {
				insertToRows.add(product);
				loop++;
			} else {
				loop = 1;
				productsDisplay.add(insertToRows);
				insertToRows = new ArrayList<>();
				insertToRows.add(product);
				row++;
			}
			locator++;
			if (locator == productList.size()) {
				productsDisplay.add(insertToRows);
			}
		}
		
		return productsDisplay;
	}

}
