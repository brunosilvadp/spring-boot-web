package com.bruno.boticario.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bruno.boticario.model.Product;
import com.bruno.boticario.model.Sale;
import com.bruno.boticario.model.SaleItem;
import com.bruno.boticario.repository.ProductRepository;
import com.bruno.boticario.repository.SaleItemRepository;
import com.bruno.boticario.repository.SaleRepository;

@Controller
public class SaleBiz {
	
	private final SaleRepository repository;
	private final SaleItemRepository saleItemrepository;
	private final ProductRepository productRepository;
	@Autowired
	public SaleBiz(SaleRepository repository, SaleItemRepository saleItemrepository, ProductRepository productRepository) {
		this.repository = repository;
		this.saleItemrepository = saleItemrepository;
		this.productRepository = productRepository;
	}

	
	@PostMapping("sale/store")
	@ResponseBody
	public ResponseEntity<Sale> store(@RequestBody Sale sale) {
		ArrayList<SaleItem> saleItemList = new ArrayList<SaleItem>();
		double saleTotal  = sale.getSaleItem().stream().mapToDouble(o -> o.getSaleValue() * o.getSaleQuantity()).sum();
		if(sale.getPaymentMethod() == 2 || saleTotal > sale.getClient().getCreditLimit()) {
			return ResponseEntity.status(500).build();
		}
		
		for (SaleItem saleItem : sale.getSaleItem()) {
			Product product = saleItem.getProduct();
			product = productRepository.findById(product.getId()).get();
			product.stockDecrement(saleItem.getSaleQuantity());
			productRepository.save(product);
			saleItem.setProduct(product);
			saleItemList.add(saleItemrepository.save(saleItem));
		}
		
		sale.setSaleItem(saleItemList);
		repository.save(sale);
		return ResponseEntity.ok(sale);
	}

	@GetMapping("list/client/sale")
	@ResponseBody
	ResponseEntity<List<Sale>> listSalesByClientAndPeriod(@RequestParam String name, @RequestParam Date startDate, @RequestParam Date endDate){
		// List<Sale> saleList = repository.findByClientAndSaleDate(name, startDate, endDate);
		return ResponseEntity.status(500).build();
	}

	@DeleteMapping("sale/destroy")
	@ResponseBody
	public ResponseEntity<String> destroy(@RequestParam Long code){
		Sale sale = repository.findById(code).get();
		List<SaleItem> saleItemList = sale.getSaleItem();
		for (SaleItem saleItem : saleItemList) {
			Product product = saleItem.getProduct(); 
			product.stockIncrement(saleItem.getSaleQuantity());
			productRepository.save(product);
			saleItemrepository.delete(saleItem);
		}
		repository.deleteById(code);
		return ResponseEntity.ok("Venda removida com sucesso!");
	}
}
