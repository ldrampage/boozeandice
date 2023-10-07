package com.boozeandice.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boozeandice.entity.Role;
import com.boozeandice.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepo;
	
	public Set<Role> getAll(){
		return new HashSet<Role>(roleRepo.findAll());
	}
	
	public Role getById(Long id) {
		Optional<Role> role = roleRepo.findById(id);
		if(role.isPresent())
			return role.get();
		return null;
	}

}
