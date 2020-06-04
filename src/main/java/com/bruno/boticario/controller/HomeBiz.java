package com.bruno.boticario.controller;

import com.bruno.boticario.repository.ClientRepository;
import com.bruno.boticario.repository.ProductRepository;
import com.bruno.boticario.repository.PurchaseRepository;
import com.bruno.boticario.repository.SaleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeBiz {

	private ClientRepository clientRepository;
	private SaleRepository saleRepository;
	private ProductRepository productRepository;
	private PurchaseRepository purchaseRepository;

	@Autowired
	public HomeBiz(ClientRepository clientRepository, SaleRepository saleRepository, ProductRepository productRepository, PurchaseRepository purchaseRepository) {
		this.clientRepository = clientRepository;
		this.saleRepository = saleRepository;
		this.productRepository = productRepository;
		this.purchaseRepository = purchaseRepository;
	}

	@GetMapping("/")
	public String index(Model model) {
		
		model.addAttribute("page", "home/index");
		model.addAttribute("currentPage", "home");
		model.addAttribute("salesQuantity", saleRepository.count());
		model.addAttribute("productsQuantity", productRepository.count());
		model.addAttribute("purchasesQuantity", productRepository.count());
		model.addAttribute("clientsQuantity", clientRepository.count());
		return "base/app";
	}
}
