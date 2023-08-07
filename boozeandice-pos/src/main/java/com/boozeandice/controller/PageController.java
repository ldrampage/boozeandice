package com.boozeandice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class PageController {
	
	public String index(Model model) {
		return "index";
	}
	
	public String productPage(Model model) {
		return "pages/product";
	}
	
	public String productEdit(Model model) {
		return "pages/product_edit";
	}

}
