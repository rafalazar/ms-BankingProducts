package com.rafalazar.bootcamp.app.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafalazar.bootcamp.app.client.ClienteClient;
import com.rafalazar.bootcamp.app.document.BankingProduct;
import com.rafalazar.bootcamp.app.dto.ClientDto;
import com.rafalazar.bootcamp.app.repository.BankingProductRepository;
import com.rafalazar.bootcamp.app.service.BankingProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
														
@Service
public class BankingProductServiceImpl implements BankingProductService{
	
	@Autowired
	private BankingProductRepository repo;
	
	@Autowired
	private ClienteClient client;
	
	@Override
	public Flux<BankingProduct> findAll() {
		return repo.findAll();
	}

	@Override
	public Mono<BankingProduct> findById(String id) {
		return repo.findById(id);
	}

	@Override
	public Mono<BankingProduct> save(BankingProduct bp) {
		if(bp.getJointAt() == null) {
			bp.setJointAt(new Date());
		}
		
		if(bp.getUpdateAt() == null) {
			bp.setUpdateAt(new Date());
		}
		
		return repo.save(bp);
	}

	@Override
	public Mono<BankingProduct> update(BankingProduct bp, String id) {
		return repo.findById(id)
				.flatMap(b -> {
					if(bp.getJointAt() == null) {
						b.setJointAt(new Date());
					}
					
					if(bp.getUpdateAt() == null) {
						b.setUpdateAt(new Date());
					}
					
					b.setProductName(bp.getProductName());
					b.setClientType(bp.getClientType());
					b.setNumAccount(bp.getNumAccount());
					b.setNameOwner(bp.getNameOwner());
					b.setNumDoc(bp.getNumDoc());
					b.setAmount(bp.getAmount());
					b.setDepositAmount(bp.getDepositAmount());
					b.setRetiroAmount(bp.getRetiroAmount());
					
					return repo.save(b);
				});
	}

	@Override
	public Mono<Void> delete(BankingProduct bp) {
		return repo.delete(bp);
	}

	//-------------------------------------->
	//Métodos del cliente
	@Override
	public Flux<ClientDto> findAllClients() {
		return client.findAllClients();
	}

	//---------------------------------------->
	//Métodos propios
	@Override
	public Mono<BankingProduct> findByNumDoc(String numDoc) {
		return repo.findByNumDoc(numDoc);
	}

	@Override
	public Flux<BankingProduct> findByType(String clientType) {
		return repo.findByType(clientType);
	}

}
