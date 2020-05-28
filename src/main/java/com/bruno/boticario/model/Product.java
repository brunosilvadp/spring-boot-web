package com.bruno.boticario.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Bruno Silva
 *
 */
@Document("products")
public class Product implements Comparable<Product>{
	@Id
	private String id;
	private String code;
	private String name;
	private Double unitPrice;
	private Integer stock;
	private Integer minimumStock;
	@Field("created_at")
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
            @JsonProperty("minimumStock") Integer minimumStock){
		this.code = code;
		this.name = name;
		this.unitPrice = unitPrice;
		this.stock = stock;
		this.minimumStock = minimumStock;
    }

	public Product(String id, String code, String name, Double unitPrice, Integer stock, Integer minimumStock) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.unitPrice = unitPrice;
		this.stock = stock;
		this.minimumStock = minimumStock;
	}

	public String getId() {
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
	
	public void stockDecrement(Integer quantity) {
		if(this.stock > 0) {
			this.stock -= quantity;			
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
