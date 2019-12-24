package com.current.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.current.account.model.CurrentEntity;
import com.current.account.model.EntityTransaction;
import com.current.account.repository.ICurrentRepository;
import com.current.account.webclient.CallWebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CurrentServiveImpl  implements ICurrentService{
	


	@Autowired
	ICurrentRepository repository;
	
	@Autowired
	@Qualifier("webClient")
	CallWebClient webClient;
	
	EntityTransaction transaction;
	List<EntityTransaction> listTransaction;
	List<String> doc;
	Double commi;
	int n;
	Boolean ope;
	@Override
	public Flux<CurrentEntity> allCurrent() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Mono<CurrentEntity> saveCurrent(final CurrentEntity current) {
		// TODO Auto-generated method stub
		doc = new ArrayList<>();
		
		if(current.getTypeCli().equals("B")) {
			repository.save(current).subscribe();
			return Mono.just(current);
		}else  {
			
			current.getHeads().forEach(head -> doc.add(head.getDocClien()));
			 return repository.findBytitularesByDocProfiles(doc,"S",current.getProfile(),current.getBank())
					.switchIfEmpty(repository.save(current).flatMap(sv->{
				return Mono.just(sv);
			})).next();
		}
			
	}
		
	@Override
	public Mono<CurrentEntity> updCurrent(final CurrentEntity current) {
		// TODO Auto-generated method stub
		return repository.save(current);
	}

	@Override
	public Mono<Void> dltCurrent(String id) {
		return repository.deleteById(id);
	}

	@Override
	public Mono<CurrentEntity> findByNumAcc(String numAcc) {
		// TODO Auto-generated method stub
		return repository.findByNumAcc(numAcc);
	}

	@Override
	public Mono<EntityTransaction> opeCurrent(String numAcc, String tipo, Double cash) {
		ope =false;
		return repository.findByNumAcc(numAcc)
				.flatMap(p ->{
						transaction = new EntityTransaction();
						transaction.setCashA(p.getCash());
										
							if(p.getNumTran() > 0) {
								p.setNumTran(p.getNumTran() -1);
								transaction.setCommi(0.0);
								if(tipo.equals("r") && p.getCash() >= cash) {
									ope = true;
									p.setCash(p.getCash() - cash);
								}else if (tipo.equals("d")){
									ope = true;
									p.setCash( p.getCash() + cash);
								}
							}else {
								
								if(tipo.equals("r") && p.getCash() >= cash + p.getCommi()) {
									ope = true;
									p.setCash(p.getCash() - cash - p.getCommi());
									transaction.setCommi(p.getCommi());
								}else if (tipo.equals("d")){
									if(p.getCash() != 0.0) {
										ope = true;
										p.setCash( p.getCash() + cash - p.getCommi());
										transaction.setCommi(p.getCommi());
									}
								}
							}
					if(ope) {
						transaction.setNumAcc(numAcc);
						transaction.setType(tipo);
						transaction.setCashO(cash);
						transaction.setCashT(p.getCash());
						transaction.setDateTra(new Date());
					    repository.save(p).subscribe();
						return Mono.just(transaction);
					}else {
						return Mono.just(transaction);
					}
						
				});
	}

	@Override
	public Flux<CurrentEntity> findByDoc(String doc) {
		// TODO Auto-generated method stub
		return repository.findByDoc(doc);
	}

	@Override
	public Mono<EntityTransaction> opeMovement(String numAcc, String numDest, Double cash,String type) {
		// TODO Auto-generated method stub
		ope = false;
		transaction = new EntityTransaction();
		return repository.findByNumAcc(numAcc).flatMap(p ->{
			
			if(p.getCash() >= cash && p.getNumTran() > 0) {
				 commi = 0.0;
				 n = 1;
				 ope = true;
			}else if(p.getCash() >= cash + p.getCommi() && p.getNumTran() == 0){
				commi = p.getCommi();
				n = 0;
				ope = true;
			}

			
			if(ope) {
				if(type.equals("CC")) {
					return	webClient.payCredit(transaction, p, numAcc, numDest, cash,commi,n);
				}else if(type.equals("SA")) {
					return webClient.opeSaving(transaction, p, numAcc, numDest, cash,commi,n);
				}else if(type.equals("CA")) {
					return repository.findByNumAcc(numDest).flatMap(currentDest ->{
						
						transaction.setCashA(p.getCash());
						p.setNumTran(p.getNumTran() - n);
						p.setCash(p.getCash() - cash - commi);
						transaction.setCashO(cash);
						transaction.setCashT(p.getCash());
						transaction.setNumAcc(numAcc);
						transaction.setCommi(commi);
						transaction.setType("r");
						transaction.setDateTra(new Date());
						
						currentDest.setCash(currentDest.getCash() + cash);
						
						return repository.save(currentDest).flatMap(t -> {
							
							return repository.save(p).flatMap(r -> {
								
								return Mono.just(transaction);
							});
						});
					});
				}
			}	
			return Mono.just(transaction);	
		});
	}

}
