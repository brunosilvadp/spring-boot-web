package com.bruno.boticario.controller;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

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

	@GetMapping("produtos")
	public String index(Model model) {
		
		model.addAttribute("page", "product/index");
		model.addAttribute("currentPage", "product");
		
		return "base/app";
	}

	@GetMapping("produtos/estoque-baixo")
	public String lowerStock(Model model) {
		
		model.addAttribute("page", "product/lower-stock");
		model.addAttribute("currentPage", "lower-stock");
		
		return "base/app";
	}

	@PostMapping("product/store")
	@ResponseBody
	public ResponseEntity<String> store(@RequestBody Product product) {
		product.setRegisterDate(new Date());
		repository.save(product);
		return ResponseEntity.ok("Produto adicionado com sucesso!");
	}
	
	@DeleteMapping("product/destroy")
	@ResponseBody
	public ResponseEntity<String> destroy(@RequestParam Long id) {
		Product product;
		try {
			product = repository.findById(id).get();
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(500).body("Produto não encontrado");
		}
		
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
		Product product = repository.findOneByCode(code);
		if(product == null){
			return ResponseEntity.status(500).body("Produto não encontrado");
		}
		return ResponseEntity.ok(product);
	}
	
	@GetMapping("product/list")
	@ResponseBody
	ResponseEntity<List<Product>> listProducts() {
		List<Product> productList = (List<Product>) repository.findAll();
		return ResponseEntity.ok(productList);
	}

	@GetMapping("product/list/lower-stock")
	@ResponseBody
	ResponseEntity<List<Product>> listByLowerStock(){
		List<Product> productList = repository.listByLowerStock();
		return ResponseEntity.ok(productList);
	}
}
