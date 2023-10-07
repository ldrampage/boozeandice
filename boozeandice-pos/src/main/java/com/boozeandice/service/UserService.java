package com.boozeandice.service;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.boozeandice.local.entity.User;
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
	
	public User getById(Long id) {
		Optional<User> userOpt = userRepo.findById(id);
		if(userOpt.isPresent()) {
			return userOpt.get();
		}
		return null;
	}
	
	public Set<User> getByJobPositionId(Long id){
		logger.debug("In getByJobPositionName() -> id: " + id);
		Set<User> userList = userRepo.findByJobPositionId(id);
		logger.debug("userList size: " + userList.size());
		return userList;

	}
	
	public User save(User user) {
		return userRepo.save(user);
	}

}
