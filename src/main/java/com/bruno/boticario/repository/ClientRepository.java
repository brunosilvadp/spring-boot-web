package com.bruno.boticario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bruno.boticario.model.Client;

@Repository
public interface ClientRepository extends MongoRepository<Client, String>{
	List<Client> findByCpf(String cpf);
}
