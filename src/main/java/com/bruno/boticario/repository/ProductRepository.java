package com.bruno.boticario.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bruno.boticario.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{

	
}
