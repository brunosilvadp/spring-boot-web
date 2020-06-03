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
            @JsonProperty("code") final String code,
			@JsonProperty("name") final String name, 
            @JsonProperty("unitPrice") final Double unitPrice, 
            @JsonProperty("stock") final Integer stock, 
            @JsonProperty("minimumStock") final Integer minimumStock){
		this.code = code;
		this.name = name;
		this.unitPrice = unitPrice;
		this.stock = stock;
		this.minimumStock = minimumStock;
    }

	// @JsonCreator
    // public Product(
    //         @JsonProperty("id") final Long id,
	// 		@JsonProperty("code") final String code,
	// 		@JsonProperty("name") final String name, 
    //         @JsonProperty("unitPrice") final Double unitPrice, 
    //         @JsonProperty("stock") final Integer stock, 
    //         @JsonProperty("minimumStock") final Integer minimumStock){
	// 	this.id = id;
	// 	this.code = code;
	// 	this.name = name;
	// 	this.unitPrice = unitPrice;
	// 	this.stock = stock;
	// 	this.minimumStock = minimumStock;
    // }

	public Product(Long id, final String code, final String name, final Double unitPrice, final Integer stock, final Integer minimumStock) {
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

	public void setCode(final String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getUnitPrice() {
		DecimalFormat df = new DecimalFormat("#,##0.00");
		return "R$ " + df.format(unitPrice);
	}

	public void setUnitPrice(final Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(final Integer stock) {
		this.stock = stock;
	}

	public Integer getMinimumStock() {
		return minimumStock;
	}

	public void setMinimumStock(final Integer minimumStock) {
		this.minimumStock = minimumStock;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(final Date registerDate) {
		this.registerDate = registerDate;
	}
	
	public void stockIncrement(final Integer quantity) {
		this.stock += quantity;
	}
	
	public void stockDecrement(final Integer quantity) {
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
	public int compareTo(final Product o) {
		return this.name.compareTo(o.name);
	}

}
