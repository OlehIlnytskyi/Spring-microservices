package com.example.clientinventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ClientInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientInventoryApplication.class, args);
	}

}
