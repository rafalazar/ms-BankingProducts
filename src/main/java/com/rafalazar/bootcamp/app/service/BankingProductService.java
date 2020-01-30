package com.rafalazar.bootcamp.app.service;


import com.rafalazar.bootcamp.app.document.BankingProduct;
import com.rafalazar.bootcamp.app.dto.ClientDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankingProductService {
	
	public Flux<BankingProduct> findAll();
	
	public Mono<BankingProduct> findById(String id);
	
	public Mono<BankingProduct> save(BankingProduct bp);
	
	public Mono<BankingProduct> update(BankingProduct bp, String id);
	
	public Mono<Void> delete(BankingProduct bp);
	//------------------------------>
	//Métodos propios
	public Mono<BankingProduct> findByNumDoc(String numDoc);
	
	public Flux<BankingProduct> findByType(String clientType);
	
	public Flux<BankingProduct> findByBank(String bank);
	
	public Mono<BankingProduct> depositAmount(String id, Double bp);
	
	public Mono<BankingProduct> retiroAmount(String id, Double bp);
	
	///-------------------->
	//Métodos del webClient
	
	public Flux<ClientDto> findAllClients();
	
	public Mono<ClientDto> createById(String id);
	
	public Mono<ClientDto> updateBank(String bank, String id);
	
}
