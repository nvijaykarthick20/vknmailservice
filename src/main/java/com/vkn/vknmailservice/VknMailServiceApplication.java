package com.vkn.vknmailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VknMailServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VknMailServiceApplication.class, args);
	}

}
