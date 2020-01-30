package com.rafalazar.bootcamp.app.client;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.rafalazar.bootcamp.app.dto.ClientDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClienteClient {
	
	@Autowired
	@Qualifier("ms-client")
	private WebClient client;
	
	public Flux<ClientDto> findAllClients(){
		return client.get().uri("/findAll")
				.retrieve()
				.bodyToFlux(ClientDto.class);
	}
	
	public Mono<ClientDto> createById(String id){
		return client.get().uri("/findById/{id}", Collections.singletonMap("id", id))
				.retrieve()
				.bodyToMono(ClientDto.class);
	}
	
	public Mono<ClientDto> updateBank(String bank, String id){
		
		return client.put()
				.uri("/updateBank/{id}",Collections.singletonMap("id", id))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(bank)
				.retrieve()
				.bodyToMono(ClientDto.class);
	}
	
	public Mono<ClientDto> save(ClientDto dto){
		return client.post()
				.uri("/create")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(dto))
				.retrieve()
				.bodyToMono(ClientDto.class);
	}

}
