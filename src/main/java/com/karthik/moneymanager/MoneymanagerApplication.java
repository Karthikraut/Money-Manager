package com.karthik.moneymanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // Enables scheduling for the application
// MoneymanagerApplication is the main class that starts the Spring Boot application
// It is annotated with @SpringBootApplication which is a convenience annotation that adds
// @Configuration, @EnableAutoConfiguration, and @ComponentScan annotations with their default attributes.
// This class is the entry point of the application.
// It contains the main method which is the starting point of the application.
@SpringBootApplication
public class MoneymanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneymanagerApplication.class, args);
	}

}
