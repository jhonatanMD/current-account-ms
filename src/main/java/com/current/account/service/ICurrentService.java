package com.current.account.service;

import com.current.account.model.CurrentEntity;
import com.current.account.model.EntityTransaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICurrentService {
	Flux<CurrentEntity> allCurrent();
	Mono<CurrentEntity> saveCurrent(final CurrentEntity current);
	Mono<CurrentEntity> updCurrent(final CurrentEntity current);
	Mono<Void> dltCurrent(String id);
	Mono<CurrentEntity> findByNumAcc(String numAcc);
	Mono<EntityTransaction> opeCurrent(String numAcc,String tipo,Double cash);
	Flux<CurrentEntity> findByDoc(String doc);
	Mono<EntityTransaction> opeMovement(String numAcc,String numCard,Double cash,String type);
}
