//package com.boozeandice.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.boozeandice.cloud.entity.CloudCashAdded;
//import com.boozeandice.cloud.entity.CloudCashDrawer;
//import com.boozeandice.cloud.repository.CashAddedCloudRepository;
//import com.boozeandice.local.entity.CashAdded;
//import com.boozeandice.local.entity.CashDrawer;
//import com.boozeandice.local.repository.CashAddedRepository;
//
//@Service
//public class SynchronizationService {
//	
//	@Autowired
//	private CashAddedRepository localCashAddedRepo;
//	
//	@Autowired
//	private CashAddedCloudRepository cloudCashAddedRepo;
//	
//	public void syncLocalToCloud() {
////		List<CashAdded> cashAddedLocalList = localCashAddedRepo.findAll();
////		for(CashAdded cashAddedLocal : cashAddedLocalList) {
////			
////			Optional<CloudCashAdded> cloudCashAddedOpt = cloudCashAddedRepo.findById(cashAddedLocal.getId());
////			if(cloudCashAddedOpt.isPresent()) {
////				CloudCashAdded cloudCashAdded = cloudCashAddedOpt.get();
////				cloudCashAdded.setCash(cashAddedLocal.getCash());
////				cloudCashAdded.setCashdrawer();
////				cloudCashAdded.setCreatedDate(cashAddedLocal.getCreatedDate());
////				cloudCashAdded.setUser(cashAddedLocal.getUser());
////			}
////		}
//	}
//	
//	
//	
//	
//	
//
//}
