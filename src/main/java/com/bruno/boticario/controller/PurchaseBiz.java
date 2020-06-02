package com.bruno.boticario.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bruno.boticario.model.Product;
import com.bruno.boticario.model.Purchase;
import com.bruno.boticario.model.PurchaseItem;
import com.bruno.boticario.repository.ProductRepository;
import com.bruno.boticario.repository.PurchaseItemRepository;
import com.bruno.boticario.repository.PurchaseRepository;

@Controller
public class PurchaseBiz {
	
	private final PurchaseRepository repository;
	private final PurchaseItemRepository purchaseItemRepository;
	private final ProductRepository productRepository;
	
	@Autowired
	public PurchaseBiz(PurchaseRepository repository, PurchaseItemRepository purchaseItemRepository, ProductRepository productRepository) {
		this.repository = repository;
		this.purchaseItemRepository = purchaseItemRepository;
		this.productRepository = productRepository;
	}

	@PostMapping("purchase/store")
	@ResponseBody
	public ResponseEntity<String> store(@RequestBody Purchase purchase) {
		ArrayList<PurchaseItem> purchaseItemList = new ArrayList<PurchaseItem>();
				
		for (PurchaseItem purchaseItem : purchase.getPurchaseItem()) {
			Product product = purchaseItem.getProduct();
			product = productRepository.findById(product.getId()).get();
			product.stockIncrement(purchaseItem.getPurchaseQuantity());
			productRepository.save(product);
			purchaseItem.setProduct(product);
			purchaseItemList.add(purchaseItemRepository.save(purchaseItem));
		}
		
		purchase.setPurchaseItem(purchaseItemList);
		repository.save(purchase);
		return ResponseEntity.ok("Compra cadastrada com sucesso!");
	}
	
	@DeleteMapping("purchase/destroy")
	@ResponseBody
	public ResponseEntity<String> destroy(@RequestParam Long code){
		Purchase purchase = repository.findById(code).get();
		List<PurchaseItem> purchaseItemList = purchase.getPurchaseItem();
		for (PurchaseItem purchaseItem : purchaseItemList) {
			Product product = purchaseItem.getProduct();
			// product = productRepository.findById(product.getId()).get();
			product.stockDecrement(purchaseItem.getPurchaseQuantity());
			productRepository.save(product);
			purchaseItemRepository.delete(purchaseItem);
		}
		repository.deleteById(code);
		return ResponseEntity.ok("Compra removida com sucesso!");
	}
}
