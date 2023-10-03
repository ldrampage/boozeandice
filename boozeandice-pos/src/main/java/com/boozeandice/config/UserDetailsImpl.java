package com.boozeandice.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.boozeandice.local.entity.Role;
import com.boozeandice.local.entity.User;

public class UserDetailsImpl implements UserDetails {
	
	private static final Logger logger = LogManager.getLogger(UserDetailsImpl.class);

	private static final long serialVersionUID = 1L;
	
	private String fname;
	private String lname;
	private String imgProfileName;
	private String username;
	private String password;
	private String[] remoteAddressess;
	
	private List<GrantedAuthority> authorities;
	
	
	public UserDetailsImpl(User user, String[] remoteAddressess) {
		
		this.fname = user.getFname();
		this.lname = user.getLname();
		this.username = user.getUsername();
		this.imgProfileName = user.getImgLocation();
		this.password = new BCryptPasswordEncoder().encode(user.getPassword());
		this.remoteAddressess = remoteAddressess;
		
		List<Role> roleList = new ArrayList<>(user.getRoles());
		
		List<GrantedAuthority> authorities = roleList.stream()
		        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().strip()))
		        .collect(Collectors.toList());
		
		this.authorities = authorities;
		
		logger.debug("fname: " + this.fname);
		logger.debug("lname: " + this.lname);
		logger.debug("username: " + this.username);
		logger.debug("remoteAddressess: " + this.remoteAddressess); 
		for(GrantedAuthority auth : authorities) {
			logger.debug("Auth: " + auth.getAuthority());
		}

		
		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getFname() {
		return fname;
	}

	public String getLname() {
		return lname;
	}

	public String getImgProfileName() {
		return imgProfileName;
	}

	public String[] getRemoteAddressess() {
		return remoteAddressess;
	}

	public void setRemoteAddressess(String[] remoteAddressess) {
		this.remoteAddressess = remoteAddressess;
	}

	
	
	
	
	
	
	

}
