//package com.boozeandice.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.boozeandice.utility.BarcodeGenerator;
//
//@Configuration
//public class BarcodeGeneraorInitializer {
//	
//	@Autowired
//	private BarcodeGenerator barGen;
//	
//	@Bean
//	public CommandLineRunner initializeDataBar() {
//		return args -> {
//			
//			barGen.generateUPCABarcode("453", "432","", 100, 50);
//			
//		};
//	}
//
//}
