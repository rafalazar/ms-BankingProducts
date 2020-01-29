package com.rafalazar.bootcamp.app.controller;

import java.net.URI;

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
import com.rafalazar.bootcamp.app.service.BankingProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bankingProduct")
public class BankingProductController {

	@Autowired
	private BankingProductService service;

	// LISTAR TODOS LOS CLIENTES
//	@GetMapping("/findAll")
//	public Mono<ResponseEntity<Flux<BankingProduct>>> findAll() {
//		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.findAll()));
//	}
	@GetMapping("/findAll")
	public Flux<BankingProduct> findAll(){
		return service.findAll();
	}

	// LISTAR UN CLIENTE POR SU ID
	@GetMapping("/findById/{id}")
	public Mono<BankingProduct> findById(@PathVariable("id") String id) {
		return service.findById(id);
	}

	// CREAR UN CLIENTE
	@PostMapping("/create")
	public Mono<ResponseEntity<BankingProduct>> create(@RequestBody BankingProduct bp) {
		return service.save(bp).map(b -> ResponseEntity.created(URI.create("/bankingProduct".concat(b.getId())))
				.contentType(MediaType.APPLICATION_JSON).body(b));
	}

	// ACTUALIZAR UN CLIENTE
	@PutMapping("/update/{id}")
	public Mono<ResponseEntity<BankingProduct>> update(@PathVariable("id") String id, @RequestBody BankingProduct bp) {
		return service.update(bp, id)
				.map(b -> ResponseEntity.created(URI.create("/bankingProduct".concat(b.getId())))
						.contentType(MediaType.APPLICATION_JSON).body(b))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	// ELIMINAR UN CLIENTE
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
	
	@GetMapping("/findByType/{clientType}")
	public Mono<ResponseEntity<Flux<BankingProduct>>> findByType(@PathVariable("clientType") String clientType){
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.findByType(clientType)));
	}

	// --------------------------------->>>>>>>>>>>>>>>>
	// Métodos del WEB-CLIENT

	@GetMapping("/findAllClients")
	Flux<ClientDto> findAllClients() {
		return service.findAllClients();
	}
}
