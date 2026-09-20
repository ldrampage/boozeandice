package com.boozeandice.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boozeandice.service.ChartService;

@Controller
@RequestMapping(path="/chart")
@Secured({"ROLE_ADMIN","ROLE_SUPERVISOR"})
public class ChartController {
	
	private static final Logger logger = LogManager.getLogger(ChartController.class);
	
	@Autowired
	private PageController pageController;
	
	@Autowired
	private ChartService chartService;
	
	@PostMapping
	public String chartPageProcess(Model model, @RequestParam Map<String,String> parameters) {
		chartService.chartPageProcess(model, parameters);
		return chartPage(model);
	}
	
	@GetMapping
	public String chartPage(Model model) {
		chartService.chartPagePrep(model);
		return pageController.chartPage(model);
		
	}

}
