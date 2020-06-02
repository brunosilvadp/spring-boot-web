package com.bruno.boticario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bruno.boticario.model.Client;
import com.bruno.boticario.model.Person;
import com.bruno.boticario.model.Sale;
import com.bruno.boticario.repository.ClientRepository;
import com.bruno.boticario.repository.SaleRepository;

@Controller
//@RequestMapping("/client")
public class ClientBiz {
	
	private final ClientRepository repository;
	private final SaleRepository saleRepository;
	
	@Autowired
	public ClientBiz(ClientRepository repository, SaleRepository saleRepository) {
		this.repository = repository;
		this.saleRepository = saleRepository;
	}
	
	@GetMapping("clientes")
	public String index(Model model) {
		
		model.addAttribute("page", "client/index");
		model.addAttribute("currentPage", "client");
		
		return "base/app";
	}
	
	@PostMapping("client/store")
	@ResponseBody
	public ResponseEntity<String> store(@RequestBody Person person) {
		System.out.println("Teste");
		Client client = (Client) person;
		List<Client> clientList = repository.findByCpf(client.getCpf());		
		
		if(!clientList.isEmpty()) {
			return ResponseEntity.status(500).body("CPF já cadastrado");
		}
		// client.setRegisterDate(new Date());
		client = repository.save(client);
		return ResponseEntity.ok("Cliente cadastrado com sucesso!");
	}
	
	@DeleteMapping("client/destroy")
	@ResponseBody
	public ResponseEntity<String> destroy(@RequestBody Person person){
		Client client = (Client) person;
		List<Sale> saleList = saleRepository.findByClient(client);
		
		if(!saleList.isEmpty()) {
			return ResponseEntity.status(500).body("O cliente não pode ser excluido, pois o mesmo já possui vendas");
		}
		
		repository.delete(client);
		return ResponseEntity.ok("Cliente deletado com sucesso!");
	}
	
	@GetMapping("client/find/cpf")
	@ResponseBody
	ResponseEntity<Object> findByCpf(@RequestParam String cpf) {
		List<Client> client = repository.findByCpf(cpf);
		if(client.isEmpty()) {
			return ResponseEntity.status(500).body("Cliente não encontrado");
		}
		return ResponseEntity.ok(client.get(0));
	}

	@GetMapping("client/list")
	@ResponseBody
	ResponseEntity<List<Client>> listClients() {
		List<Client> clientList = (List<Client>) repository.findAll();
		return ResponseEntity.ok(clientList);
	}
}
