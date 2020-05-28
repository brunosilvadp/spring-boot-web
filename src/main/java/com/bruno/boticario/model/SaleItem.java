package com.bruno.boticario.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import deserialize.SaleItemDeserialize;

/**
 * @author Bruno Silva
 *
 */
@Document("sale_items")
@JsonDeserialize(using = SaleItemDeserialize.class)
public class SaleItem {
	
	@Id
	private String code;
	@DBRef
	private Product product;
	private Integer saleQuantity;
	private Double saleValue;

	public SaleItem(Product product, Integer saleQuantity, Double saleValue) {
		this.product = product;
		this.saleQuantity = saleQuantity;
		this.saleValue = saleValue;
	}
	
	public String getCode() {
		return code;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getSaleQuantity() {
		return saleQuantity;
	}

	public void setSaleQuantity(Integer saleQuantity) {
		this.saleQuantity = saleQuantity;
	}

	public Double getSaleValue() {
		return saleValue;
	}

	public void setSaleValue(Double saleValue) {
		this.saleValue = saleValue;
	}

	@Override
	public String toString() {
		return "SaleItem [product=" + product + ", saleQuantity=" + saleQuantity + ", saleValue=" + saleValue + "]";
	}

	

}
