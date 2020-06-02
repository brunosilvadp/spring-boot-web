package com.bruno.boticario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruno.boticario.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
	List<Client> findByCpf(String cpf);
}
