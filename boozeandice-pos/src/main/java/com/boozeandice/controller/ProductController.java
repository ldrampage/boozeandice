package com.boozeandice.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boozeandice.entity.Product;
import com.boozeandice.entity.ProductCategory;
import com.boozeandice.entity.ProductStock;
import com.boozeandice.service.CategoryService;
import com.boozeandice.service.ProductService;

@Controller
@RequestMapping(path="/product")
public class ProductController {
	
	private static final Logger logger = LogManager.getLogger(ProductController.class);
	
	@Autowired
	private PageController pageController;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping(path="/products")
	public String productPage(Model model) {
		List<Product> productList = productService.getAll();
		for(Product product : productList) {
			Long overallStock = Long.valueOf(0);
			for(ProductStock stock : product.getProductStock()) {	
				overallStock = overallStock + stock.getQuantity();
			}
			product.setOverallStock(overallStock);
		}
		model.addAttribute("productList",productList);
	
		return pageController.productPage(model);
	}
	
	@GetMapping(path="/products/edit/{productId}")
	public String productEdit(Model model, @PathVariable("productId") Long productId) {
		Product product = productService.getById(productId);
		List<ProductCategory> productCategoryList = categoryService.getAll();
		logger.debug(product.getName());
		model.addAttribute("product",product);
		model.addAttribute("selectedValue", product.getProductCategory().getId());
		model.addAttribute("categoryList", productCategoryList);
		return pageController.productEdit(model);
	}
	
	@PostMapping(path="/products/editprocess")
	public String productEditProcess(Model model, @RequestParam Map<String, String> parameters) {
		
		Product product = productService.getById(Long.valueOf(parameters.get("productId")));
		product.setDescription(parameters.get("description"));
		product.setManufacturer(parameters.get("manufacturer"));
		product.setSupplier(parameters.get("supplier"));
		product.setPrice(Double.valueOf(parameters.get("price")));
		
		ProductCategory productCategory = categoryService.getById(Long.valueOf(parameters.get("categoryId")));
		
		product.setProductCategory(productCategory);
		productService.save(product);
		
		return "redirect:/product/products/edit/" + parameters.get("productId");
	}
	
	@PostMapping(path="/products")
	public String productSearch(Model model, @RequestParam("search") String search) {
		logger.debug("productSearch -> search: " + search);
		List<Product> productList = productService.searchByName(search);
		logger.debug(productList.size());
		for(Product product : productList) {
			Long overallStock = Long.valueOf(0);
			for(ProductStock stock : product.getProductStock()) {	
				overallStock = overallStock + stock.getQuantity();
			}
			product.setOverallStock(overallStock);
		}
		model.addAttribute("search",search);
		model.addAttribute("productList",productList);
		return pageController.productPage(model);

	}

}
