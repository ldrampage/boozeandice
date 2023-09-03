//package com.boozeandice.datastore;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.boozeandice.entity.Role;
//import com.boozeandice.repository.RoleRepository;
//
//import jakarta.annotation.PostConstruct;
//
//@Component
//public class RoleDataStore {
//	
//	@Autowired
//	private RoleRepository roleRepo;
//	
//	@PostConstruct
//	private void init() {
//		
//		Role role = new Role();
//		role.setName("SUPERADMIN");
//		roleRepo.save(role);
//		
//		role = new Role();
//		role.setName("ADMIN");
//		roleRepo.save(role);
//		
//		role = new Role();
//		role.setName("SUPERVISOR");
//		roleRepo.save(role);
//		
//		role = new Role();
//		role.setName("CASHIER");
//		roleRepo.save(role);
//		
//	}
//
//}
