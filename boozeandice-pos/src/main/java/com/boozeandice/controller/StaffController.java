package com.boozeandice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.boozeandice.service.StaffService;

@Controller
@RequestMapping(path="/staff")
public class StaffController {
	
	@Autowired
	private PageController pageController;
	
	@Autowired
	private StaffService staffService;
	
	@GetMapping(path="")
	public String staffPage(Model model) {
		model.addAttribute("staffList",staffService.getAll());
		return pageController.staffPage(model);
	}

}
