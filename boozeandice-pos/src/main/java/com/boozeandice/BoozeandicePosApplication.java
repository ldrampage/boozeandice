package com.boozeandice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.boozeandice"})
@SpringBootApplication
public class BoozeandicePosApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoozeandicePosApplication.class, args);
	}

}
