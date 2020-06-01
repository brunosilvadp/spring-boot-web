package com.bruno.boticario.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.bruno.boticario.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{
    @Query("{ 'id' : ?0 }")
	Optional<Product> findById(String id);
    
    Product findOneByCode(String code);

    @Query("{ 'stock' : {$lt : 'minimumStock'} }")
    List<Product> findByStockLowerMinimum(Sort sort);
}
