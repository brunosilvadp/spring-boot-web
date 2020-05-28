package com.bruno.boticario.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bruno.boticario.model.Product;
import com.bruno.boticario.model.PurchaseItem;

@Repository
public interface PurchaseItemRepository extends MongoRepository<PurchaseItem, String>{
	List<PurchaseItem> findByProduct(Product product);
	
}
