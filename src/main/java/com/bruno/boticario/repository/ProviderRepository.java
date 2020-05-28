package com.bruno.boticario.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bruno.boticario.model.Client;
import com.bruno.boticario.model.Provider;

@Repository
public interface ProviderRepository extends MongoRepository<Provider, String>{
	
	List<Provider> findByCnpj(String cnpj);
}
