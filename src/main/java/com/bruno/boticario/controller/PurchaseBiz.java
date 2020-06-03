package com.bruno.boticario.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

	@GetMapping("compras")
	public String index(Model model) {
		
		model.addAttribute("page", "purchase/index");
		model.addAttribute("currentPage", "purchase");
		
		return "base/app";
	}
	@PostMapping("purchase/store")
	@ResponseBody
	public ResponseEntity<String> store(@RequestBody Purchase purchase) {
		ArrayList<PurchaseItem> purchaseItemList = new ArrayList<PurchaseItem>();
		double purchaseTotal = purchase.getPurchaseItem().stream().mapToDouble(o -> o.getPurchaseValue() * o.getPurchaseQuantity()).sum();	
		for (PurchaseItem purchaseItem : purchase.getPurchaseItem()) {
			Product product = purchaseItem.getProduct();
			product = productRepository.findById(product.getId()).get();
			product.stockIncrement(purchaseItem.getPurchaseQuantity());
			productRepository.save(product);
			purchaseItem.setProduct(product);
			purchaseItemList.add(purchaseItemRepository.save(purchaseItem));
		}
		purchase.setTotal(purchaseTotal);
		purchase.setPurchaseItem(purchaseItemList);
		repository.save(purchase);
		return ResponseEntity.ok("Compra cadastrada com sucesso!");
	}
	
	@DeleteMapping("purchase/destroy")
	@ResponseBody
	public ResponseEntity<String> destroy(@RequestParam Long id){
		Purchase purchase = repository.findById(id).get();
		List<PurchaseItem> purchaseItemList = purchase.getPurchaseItem();
		for (PurchaseItem purchaseItem : purchaseItemList) {
			Product product = purchaseItem.getProduct();
			// product = productRepository.findById(product.getId()).get();
			product.stockDecrement(purchaseItem.getPurchaseQuantity());
			productRepository.save(product);
			purchaseItemRepository.delete(purchaseItem);
		}
		repository.deleteById(id);
		return ResponseEntity.ok("Compra removida com sucesso!");
	}

	@GetMapping("list/purchases")
	ResponseEntity<Object> listPurchases(@RequestParam String providerName, @RequestParam String startDate, @RequestParam String endDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date parsedStartDate;
		Date parsedEndDate;
		try {
			parsedStartDate = sdf.parse(startDate.replace("-", "/"));
			parsedEndDate = sdf.parse(endDate.replace("-", "/"));
		} catch (ParseException e) {
			return ResponseEntity.status(500).body("Ocorreu um erro ao efetuar a busca. Contate o administrador");
		}
		providerName = "%" + providerName + "%";
		List<Purchase> purchaseList = repository.findByProviderAndSaleDateInterval(providerName, parsedStartDate, parsedEndDate);
		return ResponseEntity.ok(purchaseList);
	}
}
