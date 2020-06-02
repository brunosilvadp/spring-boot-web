package com.bruno.boticario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bruno.boticario.model.Provider;
import com.bruno.boticario.model.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{
	Purchase findOneById(Long id);
	
	List<Purchase> findByProvider(Provider provider);
	
}
