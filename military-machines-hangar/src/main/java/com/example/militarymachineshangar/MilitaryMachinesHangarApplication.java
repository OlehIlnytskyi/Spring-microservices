package com.example.militarymachineshangar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MilitaryMachinesHangarApplication {

	public static void main(String[] args) {
		SpringApplication.run(MilitaryMachinesHangarApplication.class, args);
	}

}
