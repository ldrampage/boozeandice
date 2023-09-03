package com.boozeandice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class PageController {
	
	@Value("${business_name}")
	private String business_name;
	
	public String index(Model model) {
		model.addAttribute("business_name", business_name);
		return "pages/index";
	}
	
	public String checkoutPage(Model model) {
		model.addAttribute("business_name", business_name);
		return "pages/checkout";
	}
	
	public String invoicePage(Model model) {
		model.addAttribute("business_name", business_name);
		return "pages/invoice";
	}
	
	/**
	 * 
	 * Cashdrawer
	 * 
	 */
	
	public String cashdrawerPage(Model model) {
		model.addAttribute("business_name", business_name);
		return "pages/cashdrawer/cashdrawer";
	}
	
	public String cashDrawerCreate(Model model) {
		model.addAttribute("business_name", business_name);
		return "pages/cashdrawer/cashdrawer_create";
	}
	
	/**
	 * 
	 * Transaction
	 * 
	 */
	
	public String transactionPage(Model model) {
		model.addAttribute("business_name", business_name);
		return "pages/transaction/transaction";
	}
	
	public String transactionViewPage(Model model) {
		model.addAttribute("business_name", business_name);
		return "pages/transaction/transaction_view";
	}
	
	/**
	 * 
	 * Product 
	 *
	 */
	
	public String productPage(Model model) {
		model.addAttribute("business_name", business_name);
		return "pages/product/product";
	}
	
	public String productEdit(Model model) {
		model.addAttribute("business_name", business_name);
		return "pages/product/product_edit";
	}
	
	public String productAdd(Model model) {
		model.addAttribute("business_name", business_name);
		return "pages/product/product_add";
	}
	
	public String productStocks(Model model) {
		model.addAttribute("business_name", business_name);
		return "pages/product/product_stock"; 
	}
	
	public String productStocksAdd(Model model) {
		model.addAttribute("business_name", business_name);
		return "pages/product/product_stock_add";
	}
	
	public String productView(Model model) {
		model.addAttribute("business_name", business_name);
		return "pages/product/product_view";
	}
	
	public String productCategory(Model model) {
		model.addAttribute("business_name", business_name);
		return "pages/product/product_category";
	}
	
	public String productCategoryCreatePage(Model model) {
		model.addAttribute("business_name", business_name);
		return "pages/product/product_category_add";
	}
	
	public String productCategoryEditPage(Model model) {
		model.addAttribute("bussiness_name", business_name);
		return "pages/product/product_category_edit";
	}
	
	/*
	 * 
	 * Customer 
	 * 
	 */
	
	public String customerPage(Model model) {
		model.addAttribute("business_name", business_name);
		return "pages/customer/customer";
	}
	
	public String customerEditPage(Model model) {
		model.addAttribute("business_name", business_name);
		return "pages/customer/customer_edit";
	}
	
	/**
	 * 
	 * Staff
	 * 
	 */
	
	public String staffPage(Model model) {
		model.addAttribute("business_name", business_name);
		return "pages/staff/staff";
	}
	
	public String createAccountPage(Model model) {
		return "pages/staff/createaccount";
	}
	
	/**
	 * 
	 * Reports
	 * 
	 */
	
	public String reportsPage(Model model) {
		return "pages/reports/reports";
	}

}
