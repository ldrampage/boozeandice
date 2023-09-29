package com.boozeandice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/chart")
public class ChartController {
	
	@Autowired
	private PageController pageController;
	
	@GetMapping(path="/")
	public String chartPage(Model model) {
		
		return pageController.chartPage(model);
		
	}

}
