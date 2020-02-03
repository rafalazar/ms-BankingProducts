package com.rafalazar.bootcamp.app.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.rafalazar.bootcamp.app.dto.CreditDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditClient {

	@Autowired
	@Qualifier("ms-creditProduct")
	private WebClient client;

	public Flux<CreditDto> findAllProducts() {
		return client.get().uri("/findAll").retrieve().bodyToFlux(CreditDto.class);
	}
	
	public Mono<CreditDto> findById(String id){
		return client.get()
				.uri("/findById/{id}", Collections.singletonMap("id", id))
				.retrieve()
				.bodyToMono(CreditDto.class);
	}

	//Método cliente para depositar en la cuenta crédito
	public Mono<CreditDto> depositC(Double amount, String numberAccount) {

		Map<String, String> path = new HashMap<String, String>();

		path.put("amount", Double.toString(amount));
		path.put("numberAccount", numberAccount);

		return client.put()
				.uri("/depositC/{amount}/{numberAccount}", path)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(CreditDto.class);
	}

	//Método cliente para retirar en la cuenta crédito
	public Mono<CreditDto> retiroC(Double amount, String numberAccount) {
		
		Map<String, String> path = new HashMap<String, String>();

		path.put("amount", Double.toString(amount));
		path.put("numberAccount", numberAccount);
		
		return client.put()
				.uri("/retiroC/{amount}/{numberAccount}", path)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(CreditDto.class);
	}
	
	public Mono<CreditDto> save(CreditDto dto){
		return client.post()
				.uri("/create")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(dto))
				.retrieve()
				.bodyToMono(CreditDto.class);
	}

}
