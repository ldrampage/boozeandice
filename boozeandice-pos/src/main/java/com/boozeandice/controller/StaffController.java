package com.boozeandice.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boozeandice.entity.JobPosition;
import com.boozeandice.entity.Role;
import com.boozeandice.entity.User;
import com.boozeandice.service.JobPositionService;
import com.boozeandice.service.RoleService;
import com.boozeandice.service.StaffService;

@Controller
@RequestMapping(path="/staff")
public class StaffController {
	
	private static final Logger logger = LogManager.getLogger(StaffController.class);
	
	@Autowired
	private PageController pageController;
	
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private JobPositionService jobPosService;
	
	@GetMapping(path="")
	public String staffPage(Model model) {
		model.addAttribute("staffList", staffService.getAll());
		return pageController.staffPage(model);
	}
	
	@PostMapping(path="/createaccount")
	public String createAccountPage(Model model, @RequestParam Map<String, String> parameters, @RequestParam(name = "roles[]",  required = false) List<String> roles,
			@RequestParam(name="jobpositions[]", required = false) List<String> jobPositions) {
		
		Set<Role> existingRoles = roleService.getAll();
		Set<JobPosition> existingJobPositions = jobPosService.getAll();
		
		if(parameters.get("create_account_btn") != null) {
			User user = this.createAccount(parameters, roles, jobPositions);
		}
		
		model.addAttribute("existingJobPositions",existingJobPositions);
		model.addAttribute("existingRoles", existingRoles);
		return pageController.createAccountPage(model);
	}
	
	/**
	 * 
	 * Class utilities
	 * 
	 * 
	 */
	
	private User createAccount(Map<String, String> parameters, List<String> roles, List<String> jobPositions) {
		User user = null;
		logger.debug("Start createAccount()");
		
		for(Map.Entry<String, String> param : parameters.entrySet()) {
			logger.debug(param.getKey() + ": " + param.getValue());
		}
		
		for(String role : roles) {
			logger.debug("role: " + role);
		}
		
		for (String jobPos : jobPositions){
			logger.debug("Position: " + jobPos);
		}
		
		
		
		logger.debug("End createAccount()");
		return user;
	}

}
