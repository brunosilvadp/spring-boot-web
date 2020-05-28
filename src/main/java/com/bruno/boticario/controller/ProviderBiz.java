package com.bruno.boticario.controller;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bruno.boticario.model.Person;
import com.bruno.boticario.model.Provider;
import com.bruno.boticario.model.Purchase;
import com.bruno.boticario.repository.ProviderRepository;
import com.bruno.boticario.repository.PurchaseRepository;

@Controller
public class ProviderBiz {
	
	private final ProviderRepository repository;
	private final PurchaseRepository purchaseRepository;
	
	@Autowired
	public ProviderBiz(ProviderRepository repository, PurchaseRepository purchaseRepository) {
		this.repository = repository;
		this.purchaseRepository = purchaseRepository;
		
	}
	
	@GetMapping("fornecedores")
	public String index(Model model) {
		
		model.addAttribute("page", "provider/index");
		model.addAttribute("currentPage", "provider");
		
		return "base/app";
	}
	
	@PostMapping("provider/store")
	@ResponseBody
	public ResponseEntity<String> store(@RequestBody Person person) {
		Provider provider = (Provider) person;
		List<Provider> providerList = repository.findByCnpj(provider.getCnpj());		
		
		if(!providerList.isEmpty()) {
			return ResponseEntity.status(500).body("CNPJ já cadastrado");
		}
		provider.setRegisterDate(DateTime.now());
		provider = repository.save(provider);
		return ResponseEntity.ok("Fornecedor cadastrado com sucesso!");
	}
	
	@DeleteMapping("provider/destroy")
	@ResponseBody
	public ResponseEntity<String> destroy(@RequestBody Person person){
		Provider provider = (Provider) person;
		List<Purchase> purchaseList = purchaseRepository.findByProvider(provider);
		
		if(!purchaseList.isEmpty()) {
			return ResponseEntity.status(500).body("O fornecedor não pode ser excluido, pois o mesmo já possui compras");
		}
		
		repository.delete(provider);
		return ResponseEntity.ok("Fornecedor deletado com sucesso!");
	}
	
	@GetMapping("provider/find/cnpj")
	@ResponseBody
	ResponseEntity<Object> findByCnpj(@RequestParam String cnpj) {
		List<Provider> provider = repository.findByCnpj(cnpj);
		
		if(provider.isEmpty()) {
			return ResponseEntity.status(500).body("Cliente não encontrado");
		}
		return ResponseEntity.ok(provider.get(0));
	}
	
	@GetMapping("provider/list")
	@ResponseBody
	ResponseEntity<List<Provider>> listProviders() {
		List<Provider> providerList = repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
		return ResponseEntity.ok(providerList);
	}
}
