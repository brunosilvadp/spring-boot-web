package com.bruno.boticario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruno.boticario.model.Product;
import com.bruno.boticario.model.PurchaseItem;

@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long>{
	List<PurchaseItem> findByProduct(Product product);
	
}
