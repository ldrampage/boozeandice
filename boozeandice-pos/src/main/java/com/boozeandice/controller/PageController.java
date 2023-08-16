package com.boozeandice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class PageController {
	
	public String index(Model model) {
		return "pages/index";
	}
	
	public String checkoutPage(Model model) {
		return "pages/checkout";
	}
	
	public String invoicePage(Model model) {
		return "pages/invoice";
	}
	
	/**
	 * 
	 * Cashdrawer
	 * 
	 */
	
	public String cashdrawerPage(Model model) {
		return "pages/cashdrawer/cashdrawer";
	}
	
	public String cashDrawerCreate(Model model) {
		return "pages/cashdrawer/cashdrawer_create";
	}
	
	/**
	 * 
	 * Transaction
	 * 
	 */
	
	public String transactionPage(Model model) {
		return "pages/transaction/transaction";
	}
	
	/**
	 * 
	 * Product 
	 *
	 */
	
	public String productPage(Model model) {
		return "pages/product/product";
	}
	
	public String productEdit(Model model) {
		return "pages/product/product_edit";
	}
	
	public String productAdd(Model model) {
		return "pages/product/product_add";
	}
	
	public String productStocks(Model model) {
		return "pages/product/product_stock"; 
	}
	
	public String productStocksAdd(Model model) {
		return "pages/product/product_stock_add";
	}
	
	public String productView(Model model) {
		return "pages/product/product_view";
	}
	
	public String productCategory() {
		return "pages/product/product_category";
	}
	
	/*
	 * 
	 * Customer 
	 * 
	 */
	
	public String customerPage(Model model) {
		return "pages/customer/customer";
	}
	
	public String customerEditPage(Model model) {
		return "pages/customer/customer_edit";
	}
	
	

}
