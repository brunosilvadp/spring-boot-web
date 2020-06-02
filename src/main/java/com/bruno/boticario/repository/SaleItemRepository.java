package com.bruno.boticario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruno.boticario.model.Product;
import com.bruno.boticario.model.SaleItem;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Long>{
	List<SaleItem> findByProduct(Product product);
	
}
