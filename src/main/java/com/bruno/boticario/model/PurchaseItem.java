package com.bruno.boticario.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Bruno Silva
 *
 */
public class PurchaseItem {
	@Id
	private String purchaseCode;
	@DBRef
	private Product product;
	private Integer purchaseQuantity;
	private Double purchaseValue;
	
//	public PurchaseItem(Product product, Integer purchaseQuantity, Double purchaseValue) {
//		this.product = product;
//		this.purchaseQuantity = purchaseQuantity;
//		this.purchaseValue = purchaseValue;
//	}
//	
	@JsonCreator
	public PurchaseItem(@JsonProperty("product") Product product, 
				@JsonProperty("purchaseQuantity")  Integer purchaseQuantity, 
				@JsonProperty("purchaseValue") Double purchaseValue) {
		this.product = product;
		this.purchaseQuantity = purchaseQuantity;
		this.purchaseValue = purchaseValue;
	}
	
	public String getPurchaseCode() {
		return purchaseCode;
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
