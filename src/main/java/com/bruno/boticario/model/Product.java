package com.bruno.boticario.model;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.PersistenceConstructor;

import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bruno.boticario.exception.SisComException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Bruno Silva
 *
 */
@Entity
@Table(name = "products")
public class Product implements Comparable<Product>{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String code;
	private String name;
	@Column(name = "unit_price")
	private Double unitPrice;
	private Integer stock;
	@Column(name = "minimum_stock")
	private Integer minimumStock;
	@CreationTimestamp
	@Column(name = "created_at")
	private Date registerDate;

	@PersistenceConstructor
	public Product() {
		
	}
	
	@JsonCreator
    public Product(
            @JsonProperty("code") String code,
			@JsonProperty("name") String name, 
            @JsonProperty("unitPrice") Double unitPrice, 
            @JsonProperty("stock") Integer stock, 
			@JsonProperty("quantity") Integer quantity,
            @JsonProperty("minimumStock") Integer minimumStock){
		this.code = code;
		this.name = name;
		this.unitPrice = unitPrice;
		this.stock = stock;
		this.minimumStock = minimumStock;
    }

	// @JsonCreator
    // public Product(
    //         @JsonProperty("id") Long id,
	// 		@JsonProperty("code") String code,
	// 		@JsonProperty("name") String name, 
    //         @JsonProperty("unitPrice") Double unitPrice, 
    //         @JsonProperty("stock") Integer stock, 
    //         @JsonProperty("minimumStock") Integer minimumStock){
	// 	this.id = id;
	// 	this.code = code;
	// 	this.name = name;
	// 	this.unitPrice = unitPrice;
	// 	this.stock = stock;
	// 	this.minimumStock = minimumStock;
    // }

	public Product(Long id, String code, String name, Double unitPrice, Integer stock, Integer minimumStock) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.unitPrice = unitPrice;
		this.stock = stock;
		this.minimumStock = minimumStock;
	}

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnitPriceFormated() {
		DecimalFormat df = new DecimalFormat("#,##0.00");
		return "R$ " + df.format(unitPrice);
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getMinimumStock() {
		return minimumStock;
	}

	public void setMinimumStock(Integer minimumStock) {
		this.minimumStock = minimumStock;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	public void stockIncrement(Integer quantity) {
		this.stock += quantity;
	}
	
	public void stockDecrement(Integer quantity) throws SisComException {
		this.stock -= quantity;			
		if(this.stock < 0) {
			throw new SisComException("Estoque Insuficiente.");
		}
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", code=" + code + " name=" + name + ", unitPrice=" + unitPrice + ", stock=" + stock
				+ ", minimumStock=" + minimumStock + ", registerDate=" + registerDate + "]";
	}
	
	@Override
	public int compareTo(Product o) {
		return this.name.compareTo(o.name);
	}

}
