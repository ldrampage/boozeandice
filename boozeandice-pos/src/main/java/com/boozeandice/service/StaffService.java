package com.boozeandice.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.boozeandice.entity.JobPosition;
import com.boozeandice.entity.Role;
import com.boozeandice.entity.User;
import com.boozeandice.repository.UserRepository;

@Service
public class StaffService implements Serializable {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private JobPositionService jobPosService;

	@Autowired
	private UserService userService;

	@Value("${upload.profile.directory}")
	private String uploadDirectory;
	
	private static final Logger logger = LogManager.getLogger(StaffService.class);
	
	public void createAccountPage(Model model, Map<String, String> parameters,List<String> roles,MultipartFile file) {

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
	}
	
	public void profileViewProcess(Model model, Map<String, String> parameters) {
		User user = null;
		Map<String, String> message = new HashMap<String, String>();
		if(parameters.get("edit_userinfo_btn") != null) {
			for(Map.Entry<String, String> param : parameters.entrySet()) {
				logger.debug(param.getKey() + ": " + param.getValue());
			}
			user = userService.getByUsername(parameters.get("username"));
			user.setFname(parameters.get("fname"));
			user.setMname(parameters.get("mname"));
			user.setLname(parameters.get("lname"));
			user.setUsername(parameters.get("username"));
			user.setMobileNumber(parameters.get("mobile_number"));
			user.setAbout(parameters.get("about"));
			userService.save(user);
		}
		
		if(parameters.get("change_password_btn") != null) {
			for(Map.Entry<String, String> param : parameters.entrySet()) {
				logger.debug(param.getKey() + ": " + param.getValue());
			}
			user = userService.getByUsername(parameters.get("username"));
			if(parameters.get("current_password").equals(user.getPassword())) {
				user.setPassword(parameters.get("new_password"));
				userService.save(user);
				message.put("status", "success");
				message.put("message", "Congratulations. Your new password has been set!");
			} else {
				message.put("status", "error");
				message.put("message", "Could not change the password. Current password is incorrect!");
			}
		}
		
		model.addAttribute("message", message);
		model.addAttribute("id", user.getId().toString());
	}
	
	public Set<User> getAll(){
		return new HashSet<User>(userRepo.findAll());
	}
	
	public User getById(Long id) {
		Optional<User> userOpt = userRepo.findById(id);
		if(userOpt.isPresent()) {
			return userOpt.get();
		}
		
		return null;
	}
	
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

		if (parameters.get("jobpositions") != null)
			user.setJobPosition(jobPosService.getById(Long.valueOf(parameters.get("jobpositions"))));

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
