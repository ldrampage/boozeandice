package com.boozeandice.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.boozeandice.entity.JobPosition;
import com.boozeandice.entity.Role;
import com.boozeandice.entity.User;
import com.boozeandice.service.JobPositionService;
import com.boozeandice.service.RoleService;
import com.boozeandice.service.StaffService;
import com.boozeandice.service.UserService;

@Controller
@RequestMapping(path = "/staff")
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

	@Autowired
	private UserService userService;

	@Value("${upload.profile.directory}")
	private String uploadDirectory;

	@GetMapping(path = "")
	public String staffPage(Model model) {
		model.addAttribute("staffList", staffService.getAll());
		return pageController.staffPage(model);
	}

	@PostMapping(path = "/createaccount")
	public String createAccountPage(Model model, @RequestParam Map<String, String> parameters,
			@RequestParam(name = "roles[]", required = false) List<String> roles,
			@RequestParam(name = "file", required = false) MultipartFile file) {

		Set<Role> existingRoles = roleService.getAll();
		Set<JobPosition> existingJobPositions = jobPosService.getAll();

		Map<String, String> message = new HashMap<String, String>();
		if (parameters.get("create_account_btn") != null) {
			try {
				this.createAccount(parameters, roles, file);
				message.put("status", "success");
			} catch (Exception e) {
				message.put("status", "error");
				message.put("message", e.getMessage());
				e.printStackTrace();
			}
		}
		model.addAttribute("existingJobPositions", existingJobPositions);
		model.addAttribute("existingRoles", existingRoles);
		model.addAttribute("message", message);
		return pageController.createAccountPage(model);
	}

	/**
	 * 
	 * Class utilities
	 * 
	 * 
	 */

	private User createAccount(Map<String, String> parameters, List<String> roles, MultipartFile file)
			throws Exception {
		User user = new User();
		Set<Role> newRoles = new HashSet<>();
		logger.debug("Start createAccount()");

		for (Map.Entry<String, String> param : parameters.entrySet()) {
			logger.debug(param.getKey() + ": " + param.getValue());
		}

		if (roles != null && roles.size() > 0) {
			for (String role : roles) {
				logger.debug("role: " + role);
				Role roleObject = roleService.getById(Long.valueOf(role));
				newRoles.add(roleObject);
			}
		}

		Path filePath = null;

		user.setFname(parameters.get("fname").trim());
		user.setMname(parameters.get("mname").trim());
		user.setLname(parameters.get("lname").trim());
		user.setUsername(generateUsername(parameters.get("fname"), parameters.get("mname"), parameters.get("lname")));
		user.setPassword(parameters.get("password").trim());
		user.setMobileNumber(parameters.get("mobile_number"));
		user.setRoles(newRoles);
		user.setCreatedDate(new Timestamp(System.currentTimeMillis()));

		if (parameters.get("jobPositions") != null)
			user.setJobPosition(jobPosService.getById(Long.valueOf(parameters.get("jobPositions"))));

		if (!file.isEmpty()) {
			filePath = Paths.get(uploadDirectory, file.getOriginalFilename());
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			user.setImgLocation(file.getOriginalFilename());
		}

		user = userService.save(user);

		logger.debug("End createAccount()");
		return user;
	}

	private String generateUsername(String fname, String mname, String lname) {
		StringBuilder username = new StringBuilder();
		if (fname != null && !fname.isEmpty()) {
			username.append(fname.charAt(0));
		}

		if (mname != null && !mname.isEmpty()) {
			username.append(mname.charAt(0));
		}

		if (lname != null && !lname.isEmpty()) {
			int lastNameLength = Math.min(5, lname.length());
			username.append(lname.substring(0, lastNameLength));
		}
		
		return username.toString().toLowerCase();
	}

}
