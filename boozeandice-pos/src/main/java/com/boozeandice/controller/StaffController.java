package com.boozeandice.controller;

import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;

import com.boozeandice.service.StaffService;

@Controller
@RequestMapping(path = "/staff")
@Secured({"ROLE_ADMIN","ROLE_SUPERVISOR"})
public class StaffController {

	private static final Logger logger = LogManager.getLogger(StaffController.class);

	@Autowired
	private PageController pageController;

	@Autowired
	private StaffService staffService;

	@GetMapping
	public String staffPage(Model model) {
		model.addAttribute("staffList", staffService.getAll());
		return pageController.staffPage(model);
	}
	
	@GetMapping(path="/profileview")
	public String profileViewPage(Model model, @RequestParam String id) {
		model.addAttribute("staff", staffService.getById(Long.valueOf(id)));
		return pageController.profileViewPage(model);
	}
	
	@PostMapping(path="/profileview")
	public String profileViewProcess(Model model, @RequestParam Map<String, String> parameters) {
		staffService.profileViewProcess(model, parameters);
		return profileViewPage(model,model.getAttribute("id").toString());
	}

	@PostMapping(path = "/createaccount")
	public String createAccountPage(Model model, @RequestParam Map<String, String> parameters,
			@RequestParam(name = "roles[]", required = false) List<String> roles,
			@RequestParam(required = false) MultipartFile file) {
		staffService.createAccountPage(model, parameters, roles, file);
		return pageController.createAccountPage(model);
	}

	

}
