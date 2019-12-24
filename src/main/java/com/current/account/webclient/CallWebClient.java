package com.current.account.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.current.account.model.CurrentEntity;
import com.current.account.model.EntityTransaction;
import com.current.account.repository.ICurrentRepository;

import reactor.core.publisher.Mono;

@Component
@Qualifier("webClient")
public class CallWebClient {
	
	 WebClient client = WebClient.builder().baseUrl("http://localhost:8881")
			  .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
	 
	 @Autowired
		ICurrentRepository repository;
	 
	 public Mono<EntityTransaction> payCredit(EntityTransaction transaction,CurrentEntity p  ,
			 String numAcc,String numDest,Double cash,Double commi,int n){
		return client.post().uri("/credit-card/api/updTransancionesCreditCard/"+numDest+"/p/"+cash)
				.retrieve().bodyToMono(EntityTransaction.class).flatMap(tran -> {
					
			 		transaction.setCashA(p.getCash());
					p.setNumTran(p.getNumTran() - n);
					p.setCash(p.getCash() - cash - commi);
					transaction.setCashO(cash);
					transaction.setCashT(p.getCash());
					transaction.setNumAcc(numAcc);
					transaction.setCommi(commi);
					transaction.setType("r");
					transaction.setDateTra(tran.getDateTra());
					
					return repository.save(p).flatMap(t -> {
						 return Mono.just(transaction);
					});
			});	
		}
	 
	 public Mono<EntityTransaction> opeSaving(EntityTransaction transaction,CurrentEntity p  ,String numAcc ,String numDest,Double cash
			 ,Double commi,int n){
		  
			return  client.post().uri("/saving-account/api/updTransancionSaving/"+numDest+"/d/"+cash)
				.retrieve().bodyToMono(EntityTransaction.class).flatMap(tran -> {
					
			 		transaction.setCashA(p.getCash());
			 		p.setNumTran(p.getNumTran() - n);
					p.setCash(p.getCash() - cash - commi);
					transaction.setCashO(cash);
					transaction.setCashT(p.getCash());
					transaction.setNumAcc(numAcc);
					transaction.setCommi(commi);
					transaction.setType("r");
					transaction.setDateTra(tran.getDateTra());
					
					return repository.save(p).flatMap(f -> {
						 return Mono.just(transaction);
					});
				});		
		  }
}
