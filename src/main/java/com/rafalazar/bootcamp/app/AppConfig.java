package com.rafalazar.bootcamp.app;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {
	@Bean
	@Qualifier("ms-client")
	public WebClient getCliente() {
		return WebClient.create("http://localhost:8103/clients");
	}
}
