package com.jtechmind.estore_customer_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EstoreCustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstoreCustomerServiceApplication.class, args);
	}

}
