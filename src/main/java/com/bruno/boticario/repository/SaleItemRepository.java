package com.bruno.boticario.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bruno.boticario.model.Product;
import com.bruno.boticario.model.SaleItem;

@Repository
public interface SaleItemRepository extends MongoRepository<SaleItem, String>{
	List<SaleItem> findByProduct(Product product);
	
}
