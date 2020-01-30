package com.rafalazar.bootcamp.app;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.mongodb.assertions.Assertions;
import com.rafalazar.bootcamp.app.document.BankingProduct;
import com.rafalazar.bootcamp.app.service.BankingProductService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class testBanking {
	
	@Autowired
	private WebTestClient client; 
	
	@Autowired
	private BankingProductService service;

	@Test
	void test() {
		
		
	}
	
	@Test
	public void listAll() {
		client.get().uri("/bankingProduct/findAll")
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBodyList(BankingProduct.class).consumeWith(response -> {
			List<BankingProduct> bp = response.getResponseBody();
			
			bp.forEach(b -> {
				System.out.println(b.getId());
			});
			
			//Assertions.assertThat(bp.size()>0).isTrue();
		});;
	}
	

}
