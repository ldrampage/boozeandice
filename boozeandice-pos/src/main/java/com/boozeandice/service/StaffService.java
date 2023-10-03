package com.boozeandice.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boozeandice.local.entity.User;
import com.boozeandice.local.repository.UserRepository;

@Service
public class StaffService {
	
	@Autowired
	private UserRepository userRepo;
	
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

}
