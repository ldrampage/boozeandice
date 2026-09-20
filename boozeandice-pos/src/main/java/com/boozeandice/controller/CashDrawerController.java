package com.boozeandice.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boozeandice.service.CashDrawerService;

@Controller
@RequestMapping(path = "/cashdrawer")
@Secured({"ROLE_ADMIN","ROLE_SUPERVISOR","ROLE_CASHIER"})
public class CashDrawerController {

	private static final Logger logger = LogManager.getLogger(CashDrawerController.class);

	private final CashDrawerService cashDrawerService;
	private final PageController pageController;
	
	public CashDrawerController(CashDrawerService cashDrawerService,PageController pageController) {
		this.cashDrawerService = cashDrawerService;
		this.pageController = pageController;
	}
	
	@GetMapping
	public String cashdrawerPage(Model model, String date) {
		cashDrawerService.cashDrawerPagePrepare(model, date);
		return this.pageController.cashdrawerPage(model);
	}
	
	@GetMapping(path = "/create")
	public String cashDrawerCreatePage(Model model) {
		return this.pageController.cashDrawerCreate(model);
	}

	@PostMapping
	public String cashDrawerCreateProcess(Model model, @RequestParam Map<String, String> parameters) {
		String direction = cashDrawerService.cashDrawerCreatePrepare(model, parameters);
		if(direction != null) {
			return direction;
		}
		return pageController.cashdrawerPage(model);
	}
}
