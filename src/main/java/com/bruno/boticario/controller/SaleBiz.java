package com.bruno.boticario.controller;

import java.text.DecimalFormat;
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
	public SaleBiz(final SaleRepository repository, final SaleItemRepository saleItemrepository,
			final ProductRepository productRepository) {
		this.repository = repository;
		this.saleItemrepository = saleItemrepository;
		this.productRepository = productRepository;
	}

	@GetMapping("relatorio-de-vendas-por-clientes")
	String listSalesByClient(final Model model) {
		model.addAttribute("page", "sale/show-by-client");
		model.addAttribute("currentPage", "sale-by-client-show");

		return "base/app";
	}

	@GetMapping("relatorio-de-vendas-por-vendedor")
	String listSalesBySeller(final Model model) {
		model.addAttribute("page", "sale/show-by-seller");
		model.addAttribute("currentPage", "sale-by-seller-show");

		return "base/app";
	}

	@PostMapping("sale/store")
	@ResponseBody
	public ResponseEntity<Sale> store(@RequestBody final Sale sale) {
		final ArrayList<SaleItem> saleItemList = new ArrayList<SaleItem>();
		final double saleTotal = sale.getSaleItem().stream().mapToDouble(o -> o.getSaleValue() * o.getSaleQuantity()).sum();
		if (sale.getPaymentMethod() == 2 || saleTotal > sale.getClient().getCreditLimit()) {
			return ResponseEntity.status(500).build();
		}

		for (final SaleItem saleItem : sale.getSaleItem()) {
			Product product = saleItem.getProduct();
			product = productRepository.findById(product.getId()).get();
			product.stockDecrement(saleItem.getSaleQuantity());
			productRepository.save(product);
			saleItem.setProduct(product);
			saleItemList.add(saleItemrepository.save(saleItem));
		}
		sale.setTotal(saleTotal);
		sale.setSaleItem(saleItemList);
		repository.save(sale);
		return ResponseEntity.ok(sale);
	}

	@GetMapping("list/client/sale")
	@ResponseBody
	ResponseEntity<Object> listSalesByClientAndPeriod(@RequestParam String clientName, @RequestParam final String startDate, @RequestParam final String endDate) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date parsedStartDate;
		Date parsedEndDate;
		try {
			parsedStartDate = sdf.parse(startDate.replace("-", "/"));
			parsedEndDate = sdf.parse(endDate.replace("-", "/"));
		} catch (final ParseException e) {
			return ResponseEntity.status(500).body("Ocorreu um erro ao efetuar a busca. Contate o administrador");
		}
		clientName = "%" + clientName + "%";
		final List<Sale> saleList = repository.findByClientAndSaleDateInterval(clientName, parsedStartDate, parsedEndDate);
		return ResponseEntity.ok(saleList);
	}

	@GetMapping("list/seller/sale")
	@ResponseBody
	ResponseEntity<Object> listSalesBySellerAndPeriod(@RequestParam String sellerName, @RequestParam final String startDate, @RequestParam final String endDate) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date parsedStartDate;
		Date parsedEndDate;
		try {
			parsedStartDate = sdf.parse(startDate.replace("-", "/"));
			parsedEndDate = sdf.parse(endDate.replace("-", "/"));
		} catch (final ParseException e) {
			return ResponseEntity.status(500).body("Ocorreu um erro ao efetuar a busca. Contate o administrador");
		}
		sellerName = "%" + sellerName + "%";
		final List<Sale> saleList = repository.findBySellerAndSaleDateInterval(sellerName, parsedStartDate, parsedEndDate);
		return ResponseEntity.ok(saleList);
	}
	
	@GetMapping("report/client/sale")
	ResponseEntity<Object> clientReport(@RequestParam final String clientId, @RequestParam final String startDate, @RequestParam final String endDate){
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date parsedStartDate;
		Date parsedEndDate;
		try {
			parsedStartDate = sdf.parse(startDate.replace("-", "/"));
			parsedEndDate = sdf.parse(endDate.replace("-", "/"));
		} catch (final ParseException e) {
			return ResponseEntity.status(500).body("Ocorreu um erro ao efetuar a busca. Contate o administrador");
		}
		String[] data = repository.clientReport(Long.parseLong(clientId), parsedStartDate, parsedEndDate).split(",");
		return ResponseEntity.ok(data);
	}

	@DeleteMapping("sale/destroy")
	@ResponseBody
	public ResponseEntity<String> destroy(@RequestParam final Long id){
		final Sale sale = repository.findById(id).get();
		final List<SaleItem> saleItemList = sale.getSaleItem();
		for (final SaleItem saleItem : saleItemList) {
			final Product product = saleItem.getProduct(); 
			product.stockIncrement(saleItem.getSaleQuantity());
			productRepository.save(product);
			saleItemrepository.delete(saleItem);
		}
		repository.deleteById(id);
		return ResponseEntity.ok("Venda removida com sucesso!");
	}
}
