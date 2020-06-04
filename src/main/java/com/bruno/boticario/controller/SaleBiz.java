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

import com.bruno.boticario.exception.SisComException;
import com.bruno.boticario.model.Product;
import com.bruno.boticario.model.Sale;
import com.bruno.boticario.model.SaleItem;
import com.bruno.boticario.repository.ProductRepository;
import com.bruno.boticario.repository.SaleItemRepository;
import com.bruno.boticario.repository.SaleRepository;

@Controller
public class SaleBiz {

	private SaleRepository repository;
	private SaleItemRepository saleItemrepository;
	private ProductRepository productRepository;

	@Autowired
	public SaleBiz(SaleRepository repository, SaleItemRepository saleItemrepository,
			ProductRepository productRepository) {
		this.repository = repository;
		this.saleItemrepository = saleItemrepository;
		this.productRepository = productRepository;
	}

	@GetMapping("relatorio-de-vendas-por-clientes")
	String listSalesByClient(Model model) {
		model.addAttribute("page", "sale/show-by-client");
		model.addAttribute("currentPage", "sale-by-client-show");

		return "base/app";
	}

	@GetMapping("relatorio-de-vendas-por-vendedor")
	String listSalesBySeller(Model model) {
		model.addAttribute("page", "sale/show-by-seller");
		model.addAttribute("currentPage", "sale-by-seller-show");

		return "base/app";
	}

	@PostMapping("sale/store")
	@ResponseBody
	public ResponseEntity<String> store(@RequestBody Sale sale) {
		ArrayList<SaleItem> saleItemList = new ArrayList<SaleItem>();
		double saleTotal = sale.getSaleItem().stream().mapToDouble(o -> o.getSaleValue()).sum();
		if (sale.getPaymentMethod() == 2) {
			return ResponseEntity.status(500).body("Não é possível finalizar a venda com o método de pagamento: Venda a prazo através de Cheque pré-datado");
		}else if(saleTotal > sale.getClient().getCreditLimit()){
			return ResponseEntity.status(500).body("Não é possível finalizar a venda, pois o o valor da venda é superior ao crédito do cliente!");
		}

		for (SaleItem saleItem : sale.getSaleItem()) {
			Product product = saleItem.getProduct();
			product = productRepository.findById(product.getId()).get();
			try {
				product.stockDecrement(saleItem.getSaleQuantity());
			} catch (SisComException e) {
				return ResponseEntity.status(500).body(e.getMessageError());
			}
			productRepository.save(product);
			saleItem.setProduct(product);
			saleItemList.add(saleItemrepository.save(saleItem));
		}
		sale.setTotal(saleTotal);
		sale.setSaleItem(saleItemList);
		repository.save(sale);
		return ResponseEntity.ok("Compra cadastrada com sucesso!");
	}

	@GetMapping("list/client/sale")
	@ResponseBody
	ResponseEntity<Object> listSalesByClientAndPeriod(@RequestParam String clientName, @RequestParam String startDate, @RequestParam String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date parsedStartDate;
		Date parsedEndDate;
		try {
			parsedStartDate = sdf.parse(startDate.replace("-", "/"));
			parsedEndDate = sdf.parse(endDate.replace("-", "/"));
		} catch (ParseException e) {
			return ResponseEntity.status(500).body("Ocorreu um erro ao efetuar a busca. Contate o administrador");
		}
		clientName = "%" + clientName + "%";
		List<Sale> saleList = repository.findByClientAndSaleDateInterval(clientName, parsedStartDate, parsedEndDate);
		return ResponseEntity.ok(saleList);
	}

	@GetMapping("list/seller/sale")
	@ResponseBody
	ResponseEntity<Object> listSalesBySellerAndPeriod(@RequestParam String sellerName, @RequestParam String startDate, @RequestParam String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date parsedStartDate;
		Date parsedEndDate;
		try {
			parsedStartDate = sdf.parse(startDate.replace("-", "/"));
			parsedEndDate = sdf.parse(endDate.replace("-", "/"));
		} catch (ParseException e) {
			return ResponseEntity.status(500).body("Ocorreu um erro ao efetuar a busca. Contate o administrador");
		}
		sellerName = "%" + sellerName + "%";
		List<Sale> saleList = repository.findBySellerAndSaleDateInterval(sellerName, parsedStartDate, parsedEndDate);
		return ResponseEntity.ok(saleList);
	}
	
	@GetMapping("report/client/sale")
	ResponseEntity<Object> clientReport(@RequestParam String clientId, @RequestParam String startDate, @RequestParam String endDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date parsedStartDate;
		Date parsedEndDate;
		try {
			parsedStartDate = sdf.parse(startDate.replace("-", "/"));
			parsedEndDate = sdf.parse(endDate.replace("-", "/"));
		} catch (ParseException e) {
			return ResponseEntity.status(500).body("Ocorreu um erro ao efetuar a busca. Contate o administrador");
		}
		String[] data = repository.clientReport(Long.parseLong(clientId), parsedStartDate, parsedEndDate).split(",");
		return ResponseEntity.ok(data);
	}

	@GetMapping("report/seller/sale")
	ResponseEntity<Object> sellerReport(@RequestParam String sellerId, @RequestParam String startDate, @RequestParam String endDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date parsedStartDate;
		Date parsedEndDate;
		try {
			parsedStartDate = sdf.parse(startDate.replace("-", "/"));
			parsedEndDate = sdf.parse(endDate.replace("-", "/"));
		} catch (ParseException e) {
			return ResponseEntity.status(500).body("Ocorreu um erro ao efetuar a busca. Contate o administrador");
		}
		String[] data = repository.sellerReport(Long.parseLong(sellerId), parsedStartDate, parsedEndDate).split(",");
		return ResponseEntity.ok(data);
	}

	@DeleteMapping("sale/destroy")
	@ResponseBody
	public ResponseEntity<String> destroy(@RequestParam Long id){
		Sale sale = repository.findById(id).get();
		List<SaleItem> saleItemList = sale.getSaleItem();
		for (SaleItem saleItem : saleItemList) {
			Product product = saleItem.getProduct(); 
			product.stockIncrement(saleItem.getSaleQuantity());
			productRepository.save(product);
			saleItemrepository.delete(saleItem);
		}
		repository.deleteById(id);
		return ResponseEntity.ok("Venda removida com sucesso!");
	}
}
