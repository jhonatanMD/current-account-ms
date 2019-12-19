package com.current.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.current.account.model.CurrentEntity;
import com.current.account.model.EntityCreditCard;
import com.current.account.model.EntityTransaction;
import com.current.account.repository.ICurrentRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CurrentServiveImpl  implements ICurrentService{
	
	WebClient client = WebClient.builder().baseUrl("http://localhost:8881")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();

	@Autowired
	ICurrentRepository repository;
	
	EntityTransaction transaction;
	List<EntityTransaction> listTransaction;
	List<String> doc;
	Date dt = new Date();
	Boolean ope = false;
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
			 return repository.findBytitularesByDocProfiles(doc,"S",current.getProfile())
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
	public Mono<CurrentEntity> transactionsCurrent(String numAcc, String tipo, Double cash) {
	
		return repository.findByNumAcc(numAcc)
				.flatMap(p ->{
						transaction = new EntityTransaction();
						listTransaction = new ArrayList<>();
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
									}
								}
								
							}
						
					
					if(ope) {
						transaction.setType(tipo);
						transaction.setCashO(cash);
						transaction.setCashT(p.getCash());
						transaction.setDateTra(dt);
						
						if(p.getTransactions() != null) {
						p.getTransactions().forEach(transac-> {
							listTransaction.add(transac);
							});
						}
						listTransaction.add(transaction);
						p.setTransactions(listTransaction);
						return repository.save(p);
					}else {
						
						return Mono.just(p);
					}
						
				});
	}

	@Override
	public Flux<CurrentEntity> findByDoc(String doc) {
		// TODO Auto-generated method stub
		return repository.findByDoc(doc);
	}

	@Override
	public Mono<CurrentEntity> payCreditCard(String numAcc, String numCard, Double cash) {
		// TODO Auto-generated method stub
		return repository.findByNumAcc(numAcc).flatMap(p ->{
			transaction = new EntityTransaction();
			transaction.setCashA(p.getCash());
			if(p.getCash() >= cash) {
				p.setCash(p.getCash() - cash);
				client.post().uri("/credit-card/api/updTransancionesCreditCard/"+numCard+"/p/"+cash)
				.retrieve().bodyToMono(EntityCreditCard.class).subscribe();	
			}
			transaction.setType("pago");
			 transaction.setCashO(cash);
			 transaction.setCashT(p.getCash());
			 transaction.setDateTra(dt);
			listTransaction = new ArrayList<>();
			if(p.getTransactions()!=null)
			{
				p.getTransactions().forEach(transac-> {
					listTransaction.add(transac);
				});
			}
			listTransaction.add(transaction);
			p.setTransactions(listTransaction);
	return repository.save(p);
		});
	}

	
	
}
