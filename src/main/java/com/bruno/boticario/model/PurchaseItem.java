package com.bruno.boticario.model;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Bruno Silva
 *
 */
@Entity
@Table(name = "purchase_items")
public class PurchaseItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;
	@Column(name = "purchase_quantity")
	private Integer purchaseQuantity;
	@Column(name = "purchase_value")
	private Double purchaseValue;
	
	@JsonCreator
	public PurchaseItem(@JsonProperty("product") Product product, 
				@JsonProperty("purchaseQuantity")  Integer purchaseQuantity, 
				@JsonProperty("purchaseValue") Double purchaseValue) {
		this.product = product;
		this.purchaseQuantity = purchaseQuantity;
		this.purchaseValue = purchaseValue;
	}
	
	public PurchaseItem(){

	}

	public Long getId() {
		return id;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getPurchaseQuantity() {
		return purchaseQuantity;
	}
	public void setPurchaseQuantity(Integer purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}
	public Double getPurchaseValue() {
		return purchaseValue;
	}
	public void setPurchaseValue(Double purchaseValue) {
		this.purchaseValue = purchaseValue;
	}
	@Override
	public String toString() {
		return "PurchaseItem [purchaseQuantity=" + purchaseQuantity + ", purchaseValue=" + purchaseValue + "]";
	}
	
	
}
