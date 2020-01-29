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
					//Fecha joinAt
					if(bp.getJointAt() == null) {
						b.setJointAt(new Date());
					}else {
						b.setJointAt(bp.getJointAt());
					}
					//Fecha updateAt
					if(bp.getUpdateAt() == null) {
						b.setUpdateAt(new Date());
					}else {
						b.setUpdateAt(bp.getUpdateAt());
					}
					
					if(bp.getProductName() == null) {
						b.setProductName(b.getProductName());
					}else {
						b.setProductName(bp.getProductName());
					}
					
					//1
					if(bp.getClientType() == null) {
						b.setClientType(b.getClientType());
					}else {
						b.setClientType(bp.getClientType());
					}
					//2
					if(bp.getNumAccount() == null) {
						b.setNumAccount(b.getNumAccount());
					}else {
						b.setNumAccount(bp.getNumAccount());
					}
					//3
					if(bp.getNameOwner() == null) {
						b.setNameOwner(b.getNameOwner());
					}else {
						b.setNameOwner(bp.getNameOwner());
					}
					//4
					if(bp.getNumDoc() == null) {
						b.setNumDoc(b.getNumDoc());
					}else {
						b.setNumDoc(bp.getNumDoc());
					}
					//5
					if(bp.getAmount() == null) {
						b.setAmount(0.00);
					}else {
						b.setAmount(bp.getAmount());
					}
					//6
					if(bp.getDepositAmount() == null) {
						b.setDepositAmount(0.00);
					}else {
						b.setDepositAmount(bp.getDepositAmount());
					}
					//7
					if(bp.getRetiroAmount() == null) {
						b.setRetiroAmount(0.00);
					}else {
						b.setRetiroAmount(bp.getRetiroAmount());
					}
					
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

	@Override
	public Mono<ClientDto> createById(String id) {
		return client.createById(id);
	}

}
