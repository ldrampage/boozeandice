package com.boozeandice.service;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boozeandice.entity.User;
import com.boozeandice.repository.UserRepository;

@Service
public class UserService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LogManager.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepo;
	
	public User getByUsername(String username) {
		return userRepo.findByUsername(username);
		
	}
	
	public User save(User user) {
		return userRepo.save(user);
	}

}
