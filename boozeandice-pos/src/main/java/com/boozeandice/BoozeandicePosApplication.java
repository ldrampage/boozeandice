package com.boozeandice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ComponentScan(basePackages = {"com.boozeandice"})
@SpringBootApplication
@EnableScheduling
public class BoozeandicePosApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoozeandicePosApplication.class, args);
	}

}