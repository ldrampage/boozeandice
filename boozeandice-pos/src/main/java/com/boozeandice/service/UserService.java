package com.boozeandice.service;

import java.io.Serializable;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
		Optional<User> userOpt = userRepo.findByUsername(username);
		
		if(userOpt.isPresent()) {
			return userOpt.get();
		} else {
			throw new UsernameNotFoundException(username);
		}
		
	}
	
	public User save(User user) {
		return userRepo.save(user);
	}

}
