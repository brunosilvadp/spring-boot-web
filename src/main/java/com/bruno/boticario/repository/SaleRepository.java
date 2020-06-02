package com.bruno.boticario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruno.boticario.model.Client;
import com.bruno.boticario.model.Sale;
import com.bruno.boticario.model.Seller;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>{
	Sale findOneById(Long id);

	List<Sale> findByClient(Client client);
	
	List<Sale> findBySeller(Seller seller);
}
