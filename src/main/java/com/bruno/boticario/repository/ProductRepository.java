package com.bruno.boticario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.bruno.boticario.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    Product findOneById(Long id);
    
    Product findOneByCode(String code);
}
