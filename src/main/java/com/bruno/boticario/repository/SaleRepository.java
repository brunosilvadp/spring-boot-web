package com.bruno.boticario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.bruno.boticario.model.Client;
import com.bruno.boticario.model.Sale;
import com.bruno.boticario.model.Seller;

@Repository
public interface SaleRepository extends MongoRepository<Sale, String>{
	@Query("{ 'saleNumber' : ?0 }")
	Optional<Sale> findById(String id);
	
	List<Sale> findByClient(Client client);
	
	List<Sale> findBySeller(Seller seller);
	
}
