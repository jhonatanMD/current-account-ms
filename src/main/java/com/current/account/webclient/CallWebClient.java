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
	 
	 public Mono<EntityTransaction> payCreditSC(EntityTransaction transaction,CurrentEntity p  ,
			 String numAcc,String numCard,Double cash){
		return client.post().uri("/credit-card/api/updTransancionesCreditCard/"+numCard+"/p/"+cash)
				.retrieve().bodyToMono(EntityTransaction.class).flatMap(tran -> {
					
			 		transaction.setCashA(p.getCash());
					p.setNumTran(p.getNumTran() -1);
					p.setCash(p.getCash() - cash);
					transaction.setCashO(cash);
					transaction.setCashT(p.getCash());
					transaction.setNumAcc(numAcc);
					transaction.setCommi(0.0);
					transaction.setType("r");
					transaction.setDateTra(tran.getDateTra());
					
					repository.save(p).subscribe();
			 return Mono.just(transaction);
			
		});	
		 
	 }
	 
	 
	 public Mono<EntityTransaction> payCreditCC(EntityTransaction transaction,CurrentEntity p  ,
			 String numAcc,String numCard,Double cash){
		 return client.post().uri("/credit-card/api/updTransancionesCreditCard/"+numCard+"/p/"+cash)
					.retrieve().bodyToMono(EntityTransaction.class).flatMap(tran -> {
						
				 		transaction.setCashA(p.getCash());
						p.setCash(p.getCash() - cash - p.getCommi());
						transaction.setCashO(cash);
						transaction.setCashT(p.getCash());
						transaction.setNumAcc(numAcc);
						transaction.setCommi(p.getCommi());
						transaction.setType("r");
						transaction.setDateTra(tran.getDateTra());
						repository.save(p).subscribe();
						
				 return Mono.just(transaction);
				
			});	
	 }
	 
	 
	 public Mono<EntityTransaction> opeSaving(EntityTransaction transaction,CurrentEntity p  ,String numAcc , String type,Double cash){
		  
			return  client.post().uri("/saving-account/api/updTransancionSaving/"+numAcc+"/d/"+cash)
				.retrieve().bodyToMono(EntityTransaction.class).flatMap(tran -> {
					
			 		transaction.setCashA(p.getCash());
					p.setCash(p.getCash() - cash - p.getCommi());
					transaction.setCashO(cash);
					transaction.setCashT(p.getCash());
					transaction.setNumAcc(numAcc);
					transaction.setCommi(p.getCommi());
					transaction.setType("r");
					transaction.setDateTra(tran.getDateTra());
					repository.save(p).subscribe();
					
			 return Mono.just(transaction);
			
		});		
		  }
}
