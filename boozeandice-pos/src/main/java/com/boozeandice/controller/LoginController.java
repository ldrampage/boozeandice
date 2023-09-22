package com.boozeandice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	private static final Logger logger = LogManager.getLogger(LoginController.class);
	
	@Autowired
	private PageController pageController;
	
	@GetMapping(path="/login")
	public String loginPage(Model model) {
		return pageController.loginPage(model);
	}


}
