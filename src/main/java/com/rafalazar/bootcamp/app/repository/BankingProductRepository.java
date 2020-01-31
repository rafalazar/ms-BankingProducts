package com.rafalazar.bootcamp.app.repository;


import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.rafalazar.bootcamp.app.document.BankingProduct;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BankingProductRepository extends ReactiveMongoRepository<BankingProduct, String>{
	
	public Mono<BankingProduct> findByNumDoc(String numDoc);
	
	//public Flux<BankingProduct> findByType(String clientType);
	@Query("{'clientType' : ?0}")
	public Flux<BankingProduct> findByType(String clientType);
	
	//Buscar por banco
	@Query("{'bank' : ?0}")
	public Flux<BankingProduct> findByBank(String bank);
	
//	//RetiroBancario
//	@Query("{'id' : ?0}")
//	public Mono<BankingProduct> depositAmount(String id, Double bp);
//	//DepositoBancario
//	@Query("{'id' : ?0}")
//	public Mono<BankingProduct> retiroAmount(String id, Double bp);
	
}
 