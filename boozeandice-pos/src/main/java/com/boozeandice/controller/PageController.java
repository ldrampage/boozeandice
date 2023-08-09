package com.boozeandice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class PageController {
	
	public String index(Model model) {
		return "index";
	}
	
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

}
