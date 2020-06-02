package com.bruno.boticario.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bruno.boticario.model.Client;
import com.bruno.boticario.model.Sale;
import com.bruno.boticario.model.Seller;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>{
	Sale findOneById(Long id);

	List<Sale> findByClient(Client client);
	
	List<Sale> findBySeller(Seller seller);

	@Query(value = "SELECT * FROM sales AS s INNER JOIN clients AS c ON s.client_id = c.id WHERE c.name iLIKE ?1 AND s.sale_date >= ?2 AND s.sale_date <= ?3", nativeQuery = true)
	List<Sale> findByClientAndSaleDateInterval(String name, Date startDate, Date endDate);
}
