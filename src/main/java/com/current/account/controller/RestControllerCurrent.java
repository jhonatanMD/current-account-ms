package com.current.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.current.account.model.CurrentEntity;
import com.current.account.model.EntityTransaction;
import com.current.account.service.ICurrentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api")
public class RestControllerCurrent {

	
	@Autowired
	ICurrentService imple;
	
	@GetMapping("/getCurrents")
	public Flux<CurrentEntity> getCurrent(){
		return imple.allCurrent();
	}
	

	@GetMapping("/getCurrentNumAcc/{numAcc}")
	public Mono<CurrentEntity> getCurrentDni(@PathVariable("numAcc") String numAcc){
		return imple.findByNumAcc(numAcc);
	}
	
	@GetMapping("/getCurrentNumDoc/{numDoc}")
	public Flux<CurrentEntity> getCurrentNumDoc(@PathVariable("numDoc") String numDoc){
		return imple.findByDoc(numDoc);
	}
	
	
	@PostMapping("/postCurrent")
	public Mono<CurrentEntity> postCurrent(@RequestBody  final CurrentEntity current){
		return imple.saveCurrent(current);
	}
	


	
	@PostMapping("/updTransancionesCurrent/{numAcc}/{tipo}/{cash}")
	public Mono<EntityTransaction> updCurrentCash(@PathVariable("numAcc") String numAcc 
			,@PathVariable("tipo") String tipo ,@PathVariable("cash")  Double cash){
			return imple.opeCurrent(numAcc,tipo,cash);
	}
	
	@PostMapping("/opeMovement/{numAcc}/{numDest}/{cash}/{type}")
	Mono<EntityTransaction> opeMovement(@PathVariable("numAcc") String numAcc,
			@PathVariable("numDest") String numDest,
			@PathVariable("cash")  Double cash,@PathVariable("type") String type){
	
			return imple.opeMovement(numAcc,numDest,cash,type);

	}
	
	@PutMapping("/updCurrent/{id}")
	public Mono<CurrentEntity> updCurrent(@PathVariable("id") String id,@RequestBody final CurrentEntity current){
		current.setCodCur(id);
		return imple.updCurrent(current);
	}
	
	
	@DeleteMapping("/dltCurrent/{id}")
	public Mono<Void> dltCurrent(@PathVariable String id){
		return imple.dltCurrent(id);
	}
}
