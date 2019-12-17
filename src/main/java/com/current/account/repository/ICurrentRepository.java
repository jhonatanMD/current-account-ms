package com.current.account.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.current.account.model.CurrentEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Repository
public interface ICurrentRepository  extends ReactiveMongoRepository<CurrentEntity,String>{


	Mono<CurrentEntity> findByNumAcc(String numAcc);
	
	@Query("{ 'heads.docClien': {$in:[ ?0 ]} , 'typeCli' : ?1} ")
	Flux<CurrentEntity> findBytitularesByDoc(List<String> doc,String typeCli);
	
	@Query("{ 'heads.docClien': {$in:[ ?0 ]} , 'typeCli' : ?1 , 'profile' : ?2} ")
	Flux<CurrentEntity> findBytitularesByDocProfiles(List<String> doc,String typeCli,String profile);

	@Query("{'heads.docClien':  ?0 } ")
	Flux<CurrentEntity> findByDoc(String doc);

	
}
