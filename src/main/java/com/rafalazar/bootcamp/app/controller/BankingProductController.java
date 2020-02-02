package com.rafalazar.bootcamp.app.controller;

import java.net.URI;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafalazar.bootcamp.app.document.BankingProduct;
import com.rafalazar.bootcamp.app.dto.ClientDto;
import com.rafalazar.bootcamp.app.dto.CreditDto;
import com.rafalazar.bootcamp.app.service.BankingProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bankingProduct")
public class BankingProductController {

	@Autowired
	private BankingProductService service;

	// LISTAR TODOS LOS PRODUCTOS BANCARIOS
//	@GetMapping("/findAll")
//	public Mono<ResponseEntity<Flux<BankingProduct>>> findAll() {
//		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.findAll()));
//	}
	@GetMapping("/findAll")
	public Flux<BankingProduct> findAll(){
		return service.findAll();
	}

	// LISTAR UN PRODUCTO POR SU ID
	@GetMapping("/findById/{id}")
	public Mono<BankingProduct> findById(@PathVariable("id") String id) {
		return service.findById(id);
	}

	// CREAR UN PRODUCTO
	@PostMapping("/create")
	public Mono<ResponseEntity<BankingProduct>> create(@RequestBody BankingProduct bp) {
		return service.save(bp).map(b -> ResponseEntity.created(URI.create("/bankingProduct/".concat(b.getId())))
				.contentType(MediaType.APPLICATION_JSON).body(b));
	}

	// ACTUALIZAR UN PRODUCTO
	@PutMapping("/update/{id}")
	public Mono<ResponseEntity<BankingProduct>> update(@PathVariable("id") String id, @RequestBody BankingProduct bp) {
		return service.update(bp, id)
				.map(b -> ResponseEntity.created(URI.create("/bankingProduct/".concat(b.getId())))
						.contentType(MediaType.APPLICATION_JSON).body(b))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	// ELIMINAR UN PRODUCTO
	@DeleteMapping("/delete/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
		return service.findById(id).flatMap(b -> {
			return service.delete(b).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}

	// BUSCAR UNA CUENTA POR EL NUMDOC DEL CLIENTE
	@GetMapping("/findByNumDoc/{numDoc}")
	public Mono<ResponseEntity<BankingProduct>> findByNumDoc(@PathVariable("numDoc") String numDoc) {
		return service.findByNumDoc(numDoc)
				.map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	//BUSCAR UNA CUENTA POR EL TIPO DE CLIENTE
	@GetMapping("/findByType/{clientType}")
	public Mono<ResponseEntity<Flux<BankingProduct>>> findByType(@PathVariable("clientType") String clientType){
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.findByType(clientType)));
	}
	
	//BUSCAR UNA CUENTA POR EL BANCO AL QUE PERTENECE
	@GetMapping("/findByBank/{bank}")
	public Mono<ResponseEntity<Flux<BankingProduct>>> findByBank(@PathVariable("bank") String bank){
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.findByBank(bank)));
	}
	
	//Buscar por número de cuenta
	@GetMapping("/findByNumAccount/{numAccount}")
	public Mono<ResponseEntity<BankingProduct>> findByNumAccount(@PathVariable("numAccount") String numAccount){
		return service.findByNumAccount(numAccount)
				.map(b -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(b))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	//Depositar
	@PutMapping("/depositB/{amount}/{numAccount}")
	public Mono<BankingProduct> depositB(@PathVariable("amount") String amount, @PathVariable("numAccount") String numAccount){
		return service.depositB(Double.parseDouble(amount), numAccount);
	}
	
	//Retirar
	@PutMapping("/retiroB/{amount}/{numAccount}")
	public Mono<BankingProduct> retiroB(@PathVariable("amount") String amount, @PathVariable("numAccount") String numAccount){
		return service.retiroB(Double.parseDouble(amount), numAccount);
	}

	// --------------------------------->>>>>>>>>>>>>>>>
	// Métodos del WEB-CLIENT

	@GetMapping("/findAllClients")
	Flux<ClientDto> findAllClients() {
		return service.findAllClients();
	}
	
	@GetMapping("/createById/{id}")
	Mono<BankingProduct> createById(@PathVariable("id") String id, @RequestBody BankingProduct bp){
		//Fecha joinAt
		if(bp.getJointAt() == null) {
			bp.setJointAt(new Date());
		}else {
			bp.setJointAt(bp.getJointAt());
		}
		//Fecha updateAt
		if(bp.getUpdateAt() == null) {
			bp.setUpdateAt(new Date());
		}else {
			bp.setUpdateAt(bp.getUpdateAt());
		}
		
		return service.createById(id)
				.flatMap(p -> {
					
					//Bank
					bp.setBank(p.getBank());
					//ProductName
					bp.setProductName(bp.getProductName());
					//ClientType
					bp.setClientType(p.getType());
					//NumAccount - Random
					bp.setNumAccount(UUID.randomUUID().toString());
					//NameOwner
					bp.setNameOwner(p.getFullName());
					//NumDoc
					bp.setNumDoc(p.getNumDoc());
					
					//Amount
					if(bp.getAmount() == null) {
						bp.setAmount(0.00);
					}else {
						bp.setAmount(bp.getAmount());
					}
					
					//amountAvailable - nuevo campo!
					if(bp.getAmountAvailable() == null) {
						bp.setAmountAvailable(bp.getAmount());
					}else {
						bp.setAmountAvailable(bp.getAmountAvailable());
					}
					
					return service.save(bp);
				});
	}
	
	@PutMapping("/updateBank/{id}")
	public Mono<ClientDto> updateBank(@PathVariable("id") String id, @RequestBody ClientDto dto){
		String bank = dto.getBank();
		return service.updateBank(bank, id);
	}
	
	@PutMapping("/depositC/{amount}/{numAccountC}/{numAccountB}")
	public Mono<CreditDto> depositC(@PathVariable("amount") String amount, @PathVariable("numAccountC") String numAccountC, @PathVariable("numAccountB") String numAccountB){
		return service.depositC(Double.parseDouble(amount), numAccountC, numAccountB);
	}
	
	@PutMapping("retiroC/{amount}/{numAccountC}/{numAccountB}")
	public Mono<CreditDto> retiroC(@PathVariable("amount") String amount, @PathVariable("numAccountC") String numAccountC, @PathVariable("numAccountB") String numAccountB){
		return service.retiroC(Double.parseDouble(amount), numAccountC, numAccountB);
	}
}
