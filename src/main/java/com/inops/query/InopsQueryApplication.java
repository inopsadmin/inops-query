package com.inops.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InopsQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(InopsQueryApplication.class, args);
	}

}
