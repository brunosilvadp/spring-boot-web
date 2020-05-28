package com.bruno.boticario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.bruno.boticario.model.Provider;
import com.bruno.boticario.model.Purchase;

@Repository
public interface PurchaseRepository extends MongoRepository<Purchase, String>{
	@Query("{ 'purchaseNumber' : ?0 }")
	Optional<Purchase> findById(String id);
	
	List<Purchase> findByProvider(Provider provider);
	
}
