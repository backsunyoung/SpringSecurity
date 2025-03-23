package com.example.securityexam.securityexam;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecurityexamApplication {

	public static void main(String[] args) {

		SpringApplication.run(SecurityexamApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(){

		return args -> {


		};

	}

}
