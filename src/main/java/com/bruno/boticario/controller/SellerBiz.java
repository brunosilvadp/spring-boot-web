package com.bruno.boticario.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bruno.boticario.model.Person;
import com.bruno.boticario.model.Sale;
import com.bruno.boticario.model.Seller;
import com.bruno.boticario.repository.SaleRepository;
import com.bruno.boticario.repository.SellerRepository;

@Controller
public class SellerBiz {
	
	private final SellerRepository repository;
	private final SaleRepository saleRepository;
	
	@Autowired
	public SellerBiz(SellerRepository repository, SaleRepository saleRepository) {
		this.repository = repository;
		this.saleRepository = saleRepository;
	}

	@GetMapping("vendedores")
	public String index(Model model) {
		
		model.addAttribute("page", "seller/index");
		model.addAttribute("currentPage", "seller");
		
		return "base/app";
	}
	
	@PostMapping("seller/store")
	@ResponseBody
	public ResponseEntity<String> store(@RequestBody Person person) {
		Seller seller = (Seller) person;
		System.out.println(seller.getCpf());
		List<Seller> listSeller = repository.findByCpf(seller.getCpf());
		
		if(!listSeller.isEmpty()){
			return ResponseEntity.status(500).body("CPF já cadastrado!");
		}else if(seller.getMonthlyGoal() <= 0){
			return ResponseEntity.status(500).body("A meta mensal não pode ser igual ou menor que R$0,00");
		}
		
		seller.setRegisterDate(new Date());
		seller = repository.save(seller);
		return ResponseEntity.ok("Vendedor cadastrado com sucesso!");
	}
	
	@DeleteMapping("seller/destroy")
	@ResponseBody
	public ResponseEntity<String> destroy(@RequestBody Person person){
		Seller seller = (Seller) person;
		List<Sale> saleList = saleRepository.findBySeller(seller);
		
		if(!saleList.isEmpty()) {
			return ResponseEntity.status(500).body("O vendedor não pode ser excluido, pois o mesmo já possui vendas");
		}
		repository.delete(seller);
		return ResponseEntity.ok("Vendedor deletado com sucesso!");
	}
	
	@GetMapping("seller/list")
	@ResponseBody
	ResponseEntity<List<Seller>> listSellers() {
		List<Seller> sellerList = (List<Seller>) repository.findAll();
		return ResponseEntity.ok(sellerList);
	}
}
