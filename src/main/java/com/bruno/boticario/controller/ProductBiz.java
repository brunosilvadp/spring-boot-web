package com.bruno.boticario.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.coyote.http11.Http11AprProtocol;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bruno.boticario.model.Product;
import com.bruno.boticario.model.PurchaseItem;
import com.bruno.boticario.model.SaleItem;
import com.bruno.boticario.repository.ProductRepository;
import com.bruno.boticario.repository.PurchaseItemRepository;
import com.bruno.boticario.repository.SaleItemRepository;

@Controller
public class ProductBiz {
	
	private final ProductRepository repository;
	private final PurchaseItemRepository purchaseItemrepository;
	private final SaleItemRepository saleItemrepository;
	
	@Autowired
	public ProductBiz(ProductRepository  repository, PurchaseItemRepository purchaseItemrepository, SaleItemRepository saleItemrepository) {
		this.repository = repository;
		this.purchaseItemrepository = purchaseItemrepository;
		this.saleItemrepository = saleItemrepository;
	}

	@PostMapping("product/store")
	@ResponseBody
	public ResponseEntity<Product> store(@RequestBody Product product) {
		product.setRegisterDate(DateTime.now());
		repository.save(product);
		return ResponseEntity.ok(product);
	}
	
	@DeleteMapping("product/destroy")
	@ResponseBody
	public ResponseEntity<String> destroy(@RequestParam String code) {
		Product product = repository.findById(code).get();
		
		List<PurchaseItem> purchaseItemsList = purchaseItemrepository.findByProduct(product);
		if(!purchaseItemsList.isEmpty()) {
			return ResponseEntity.status(500).body("Esse produto não pode ser deletado, pois o mesmo já possui compras associadas!");
		}
		
		List<SaleItem> saleItemsList = saleItemrepository.findByProduct(product);
		
		if(!saleItemsList.isEmpty()) {
			return ResponseEntity.status(500).body("Esse produto não pode ser deletado, pois o mesmo já possui vendas associadas!");
		}
		
		repository.delete(product);
		return ResponseEntity.ok("Produto removido com sucesso!");
	}
	@GetMapping("product/find")
	@ResponseBody
	ResponseEntity<Object> find(@RequestParam String code) {
		try {
			Product product = repository.findById(code).get();
			return ResponseEntity.ok(product);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(500).body("Produto não encontrado");
		}
	}
	
	@GetMapping("product/list")
	@ResponseBody
	ResponseEntity<List<Product>> listProducts() {
		List<Product> productList = repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
		return ResponseEntity.ok(productList);
	}
}
