package com.rafalazar.bootcamp.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServiceBankingProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceBankingProductsApplication.class, args);
	}

}
