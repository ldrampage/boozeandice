package com.boozeandice.datastore;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boozeandice.entity.User;
import com.boozeandice.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Component
public class UserDatastore {
	
	@Autowired
	private UserRepository userRepo;
	
	@PostConstruct
	public void init() {
		User user = new User();
		user.setFname("Lyndon");
		user.setLname("Bordonada");
		user.setUsername("lxbordo");
		user.setPassword("malcom19");
		user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		userRepo.save(user);
	}

	
}
