package com.bruno.boticario.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.bruno.boticario.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{
    @Query("{ 'code' : ?0 }")
	Optional<Product> findById(String id);
	
}
