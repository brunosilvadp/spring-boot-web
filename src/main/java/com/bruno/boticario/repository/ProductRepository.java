package com.bruno.boticario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.bruno.boticario.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    Product findOneById(Long id);
    
    Product findOneByCode(String code);

    @Query(value = "SELECT * FROM products WHERE stock < minimum_stock ORDER BY name ASC", nativeQuery = true)
    List<Product> listByLowerStock();
}
