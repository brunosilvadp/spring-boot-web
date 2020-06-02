package com.bruno.boticario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruno.boticario.model.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long>{
	
	List<Provider> findByCnpj(String cnpj);
}
