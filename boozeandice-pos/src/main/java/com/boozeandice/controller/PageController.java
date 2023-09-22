package com.boozeandice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.boozeandice.config.UserDetailsImpl;
import com.boozeandice.entity.User;

import jakarta.servlet.http.HttpSession;

@Component
public class PageController {
	
	private static final Logger logger = LogManager.getLogger(PageController.class);
	
	@Value("${business_name}")
	private String business_name;
	
	public String accessDeniedPage(Model model) {
		addAttributes(model);
		return "pages/accessDenied";
	}
	
	public String index(Model model) {
		addAttributes(model);
		return "pages/index";
	}
	
	public String checkoutPage(Model model) {
		addAttributes(model);
		return "pages/checkout";
	}
	
	public String invoicePage(Model model) {
		addAttributes(model);
		return "pages/invoice";
	}
	
	public String loginPage(Model model) {
		return "pages/login";
	}
	
	/**
	 * 
	 * Cashdrawer
	 * 
	 */
	
	public String cashdrawerPage(Model model) {
		addAttributes(model);
		return "pages/cashdrawer/cashdrawer";
	}
	
	public String cashDrawerCreate(Model model) {
		addAttributes(model);
		return "pages/cashdrawer/cashdrawer_create";
	}
	
	/**
	 * 
	 * Transaction
	 * 
	 */
	
	public String transactionPage(Model model) {
		addAttributes(model);
		return "pages/transaction/transaction";
	}
	
	public String transactionViewPage(Model model) {
		addAttributes(model);
		return "pages/transaction/transaction_view";
	}
	
	/**
	 * 
	 * Product 
	 *
	 */
	
	public String productPage(Model model) {
		addAttributes(model);
		return "pages/product/product";
	}
	
	public String productEdit(Model model) {
		addAttributes(model);
		return "pages/product/product_edit";
	}
	
	public String productAdd(Model model) {
		addAttributes(model);
		return "pages/product/product_add";
	}
	
	public String productStocks(Model model) {
		addAttributes(model);
		return "pages/product/product_stock"; 
	}
	
	public String productStocksAdd(Model model) {
		addAttributes(model);
		return "pages/product/product_stock_add";
	}
	
	public String productStocksEdit(Model model) {
		addAttributes(model);
		return "pages/product/product_stock_edit";
	}
	
	public String productView(Model model) {
		addAttributes(model);
		return "pages/product/product_view";
	}
	
	public String productCategory(Model model) {
		addAttributes(model);
		return "pages/product/product_category";
	}
	
	public String productCategoryCreatePage(Model model) {
		addAttributes(model);
		return "pages/product/product_category_add";
	}
	
	public String productCategoryEditPage(Model model) {
		addAttributes(model);
		return "pages/product/product_category_edit";
	}
	
	/*
	 * 
	 * Customer 
	 * 
	 */
	
	public String customerPage(Model model) {
		addAttributes(model);
		return "pages/customer/customer";
	}
	
	public String customerEditPage(Model model) {
		addAttributes(model);
		return "pages/customer/customer_edit";
	}
	
	/**
	 * 
	 * Staff
	 * 
	 */
	
	public String staffPage(Model model) {
		addAttributes(model);
		return "pages/staff/staff";
	}
	
	public String createAccountPage(Model model) {
		addAttributes(model);
		return "pages/staff/createaccount";
	}
	
	/**
	 * 
	 * Reports
	 * 
	 */
	
	public String reportsPage(Model model) {
		addAttributes(model);
		return "pages/reports/reports";
	}
	
	// Utility
	
	public Model addAttributes(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		model.addAttribute("fname", user.getFname());
		model.addAttribute("lname", user.getLname());
		model.addAttribute("username", user.getUsername());
		model.addAttribute("imgProfileName", user.getImgProfileName());
		
		logger.debug("fname: " + model.getAttribute("fname"));
		logger.debug("lname: " + model.getAttribute("lname"));
		logger.debug("username: " + model.getAttribute("username"));

		return model;
	}

}
