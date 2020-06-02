package com.bruno.boticario.model;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import deserialize.SaleItemDeserialize;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


/**
 * @author Bruno Silva
 *
 */
@Entity
@JsonDeserialize(using = SaleItemDeserialize.class)
@Table(name = "sale_items")
public class SaleItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;
	@Column(name = "sale_quantity")
	private Integer saleQuantity;
	@Column(name = "sale_value")
	private Double saleValue;

	public SaleItem(Product product, Integer saleQuantity, Double saleValue) {
		this.product = product;
		this.saleQuantity = saleQuantity;
		this.saleValue = saleValue;
	}

	public SaleItem(Long id, Product product, Integer saleQuantity, Double saleValue) {
		this.product = product;
		this.saleQuantity = saleQuantity;
		this.saleValue = saleValue;
	}

	public SaleItem(){

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
