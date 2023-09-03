package com.boozeandice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = {"com.boozeandice"})
@EnableTransactionManagement
@SpringBootApplication
public class BoozeandicePosApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoozeandicePosApplication.class, args);
	}

}
