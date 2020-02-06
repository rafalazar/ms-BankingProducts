package com.rafalazar.bootcamp.app;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.rafalazar.bootcamp.app.document.BankingProduct;
import com.rafalazar.bootcamp.app.service.BankingProductService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BankingServiceTest {
	
	@Autowired
	private WebTestClient client;
	
	@Autowired
	private BankingProductService service;

	@Test
	void findAllBankingClients() {
		client.get()
		.uri("/bankingProduct/findAll")
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBodyList(BankingProduct.class)
		.consumeWith(res -> {
			List<BankingProduct> banking = res.getResponseBody();
			banking.forEach(b -> {
				System.out.println(b.getProductName() + " - " + b.getNameOwner());
			});
			
			Assertions.assertThat(banking.size()>0).isTrue();
		});
	}
	
	@Test
	void findByIdBanking() {
		BankingProduct banking = service.findById("5e387ec71f21901d3ac62cac").block();
		client.get().uri("/bankingProduct/findById/{id}",Collections.singletonMap("id", banking.getId()))
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON);
	}

}
