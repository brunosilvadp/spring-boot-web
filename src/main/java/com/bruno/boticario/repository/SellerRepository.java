package com.bruno.boticario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruno.boticario.model.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long>{
	List<Seller> findByCpf(String cpf);
}
