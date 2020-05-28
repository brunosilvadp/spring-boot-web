package com.bruno.boticario.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bruno.boticario.model.Seller;

@Repository
public interface SellerRepository extends MongoRepository<Seller, String>{
	List<Seller> findByCpf(String cpf);
}
