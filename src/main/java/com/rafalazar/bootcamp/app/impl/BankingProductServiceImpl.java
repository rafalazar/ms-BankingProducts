package com.rafalazar.bootcamp.app.impl;

import java.util.Date;
import java.util.UUID;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafalazar.bootcamp.app.client.ClienteClient;
import com.rafalazar.bootcamp.app.client.CreditClient;
import com.rafalazar.bootcamp.app.document.BankingProduct;
import com.rafalazar.bootcamp.app.dto.ClientDto;
import com.rafalazar.bootcamp.app.dto.CreditDto;
import com.rafalazar.bootcamp.app.repository.BankingProductRepository;
import com.rafalazar.bootcamp.app.service.BankingProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
														
@Service
public class BankingProductServiceImpl implements BankingProductService{
	
	//private static final Logger log = LoggerFactory.getLogger(BankingProductServiceImpl.class);
	
	@Autowired
	private BankingProductRepository repo;
	
	@Autowired
	private ClienteClient client;
	
	@Autowired
	private CreditClient cclient;
	
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
		}else {
			bp.setJointAt(bp.getJointAt());
		}
		
		if(bp.getUpdateAt() == null) {
			bp.setUpdateAt(new Date());
		}else {
			bp.setUpdateAt(bp.getUpdateAt());
		}
		
		if(bp.getNumAccount() == null) {
			bp.setNumAccount(UUID.randomUUID().toString());
		}else {
			bp.setNumAccount(bp.getNumAccount());
		}
		
		//Validando el amount
		if(bp.getAmount() == null) {
			bp.setAmount(0.0);
		}else {
			bp.setAmount(bp.getAmount());
		}
		
		//Nuevo campo
		if(bp.getAmountAvailable() == null) {
			bp.setAmountAvailable(bp.getAmount());
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
					
					//Bank
					if(bp.getBank() == null) {
						b.setBank(b.getBank());
					}else {
						b.setBank(bp.getBank());
					}
					//ProductName
					if(bp.getProductName() == null) {
						b.setProductName(b.getProductName());
					}else {
						b.setProductName(bp.getProductName());
					}
					
					//Type
					if(bp.getClientType() == null) {
						b.setClientType(b.getClientType());
					}else {
						b.setClientType(bp.getClientType());
					}
					//NumAccount
					if(bp.getNumAccount() == null) {
						b.setNumAccount(b.getNumAccount());
					}else {
						b.setNumAccount(bp.getNumAccount());
					}
					//NameOwner
					if(bp.getNameOwner() == null) {
						b.setNameOwner(b.getNameOwner());
					}else {
						b.setNameOwner(bp.getNameOwner());
					}
					//NumDoc
					if(bp.getNumDoc() == null) {
						b.setNumDoc(b.getNumDoc());
					}else {
						b.setNumDoc(bp.getNumDoc());
					}
					//Amount
					if(bp.getAmount() == null) {
						b.setAmount(b.getAmount());
					}else {
						b.setAmount(bp.getAmount());
					}
					
					//nuevo campo amountAvailable
					if(bp.getAmountAvailable() == null) {
						b.setAmountAvailable(b.getAmountAvailable());
					}else {
						b.setAmountAvailable(bp.getAmountAvailable());
					}
					
					return repo.save(b);
				});
	}

	@Override
	public Mono<Void> delete(BankingProduct bp) {
		return repo.delete(bp);
	}

	//-------------------------------------->
	//Métodos del cliente Client
	@Override
	public Flux<ClientDto> findAllClients() {
		return client.findAllClients();
	}
	
	@Override
	public Mono<ClientDto> createById(String id) {
		return client.createById(id);
	}
	
	@Override
	public Mono<ClientDto> updateBank(String bank, String id) {
		return client.createById(id)
				.flatMap(c -> {
					if(c.getBank() == null) {
						c.setBank(c.getBank());
					}else {
						c.setBank(bank);
					}
					
					return client.save(c);
				});
	}

	//---------------------------------------->
	//Métodos propios
	
	//---------------------Buscar_Por_NumDoc
	@Override
	public Mono<BankingProduct> findByNumDoc(String numDoc) {
		
		return repo.findByNumDoc(numDoc);
	}
	//---------------------Buscar_por_Tipo
	@Override
	public Flux<BankingProduct> findByType(String clientType) {
		
		return repo.findByType(clientType);
	}
	//-------------------Buscar_por_Banco
	@Override
	public Flux<BankingProduct> findByBank(String bank) {
		return repo.findByBank(bank);
	}

//	@Override
//	public Mono<BankingProduct> depositAmount(String id, Double bp) {
//		return repo.findById(id)
//				.flatMap(b -> {
//					if(b.getDepositAmount() != 0.0 || b.getDepositAmount() != null) {
//						b.setDepositAmount(b.getDepositAmount() + bp);
//						b.setAmount(b.getAmount() + b.getDepositAmount());
//						b.setDepositAmount(0.0);
//					}else {
//						b.setDepositAmount(bp);
//						b.setAmount(b.getAmount() + b.getDepositAmount());
//						b.setDepositAmount(0.0);
//					}
//					
//					return repo.save(b);
//				});
//	}

//	@Override
//	public Mono<BankingProduct> retiroAmount(String id, Double bp) {
//		return repo.findById(id)
//				.flatMap(b -> {
//					if(b.getRetiroAmount() != 0.0 || b.getRetiroAmount() != null) {
//						b.setRetiroAmount(b.getRetiroAmount() + bp);
//						b.setAmount(b.getAmount() - b.getRetiroAmount());
//						b.setRetiroAmount(0.0);
//					}else {
//						b.setRetiroAmount(bp);
//						b.setAmount(b.getAmount() - b.getRetiroAmount());
//						b.setRetiroAmount(0.0);
//					}
//					
//					return repo.save(b);
//				});
//	}
	
	//-------------->
	// Métodos del cliente Crédito
	@Override
	public Mono<CreditDto> deposit(Double amount, String id) {
		
		return cclient.deposit(amount, id);
		
		//return cclient.findById(id);
		//falta implementar!
//				.flatMap(c -> {
//				});
	}

	@Override
	public Mono<CreditDto> retiro(Double amount, String id) {
		return cclient.retiro(amount, id);
	}

	

}
