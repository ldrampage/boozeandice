package com.boozeandice.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.boozeandice.local.entity.POSConfig;
import com.boozeandice.local.entity.User;
import com.boozeandice.repository.POSConfigRepository;
import com.boozeandice.service.UserService;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private static final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private POSConfigRepository posConfRepo;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("loadUserByUsername() -> username: " + username);
		User user  = userService.getByUsername(username);
		Optional<POSConfig> posConfOpt = posConfRepo.findByName("remoteAddress");
		String[] remoteAddressess = {};
		String remoteAddress = "";
		if(posConfOpt.isPresent())
			remoteAddressess = posConfOpt.get().getValue().split(",");
		
		UserDetails userDetails = new UserDetailsImpl(user, remoteAddressess);
		return userDetails;
//		User user  = userService.getByUsername(username);
//		return org.springframework.security.core.userdetails
//				.User.withUsername(user.getUsername())
//				.password(new BCryptPasswordEncoder().encode(user.getPassword()))
//				.roles(user.getRoles().toString().toUpperCase())
//				.build();
		
	}

}
