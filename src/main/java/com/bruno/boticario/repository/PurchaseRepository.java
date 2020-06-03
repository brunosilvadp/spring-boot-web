package com.bruno.boticario.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bruno.boticario.model.Provider;
import com.bruno.boticario.model.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{
	Purchase findOneById(Long id);
	
	List<Purchase> findByProvider(Provider provider);
	
	@Query(value = "SELECT * FROM purchases INNER JOIN providers ON purchases.provider_id = providers.id WHERE providers.name iLIKE ?1 AND purchases.purchase_date >= ?2 AND purchases.purchase_date <= ?3 ORDER BY providers.name ASC, purchases.purchase_date DESC", nativeQuery = true)
	List<Purchase> findByProviderAndSaleDateInterval(String name, Date startDate, Date endDate);
}
