package com.bruno.boticario.model;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Bruno Silva
 *
 */
@Document("sales")
public class Sale {
	
	@Id
	private String saleNumber;
	@DBRef
	private Client client;
	@DBRef
	private Seller seller;
	@DBRef
	private ArrayList<SaleItem> saleItem;
	@Field("payment_method")
	private Integer paymentMethod;
	@Field("sale_date")
	private Date saleDate;

	public Sale(Client client, Seller seller, ArrayList<SaleItem> saleItem,
			Integer paymentMethod) {
		this.client = client;
		this.seller = seller;
		this.saleItem = saleItem;
		this.paymentMethod = paymentMethod;
	}
	
	@PersistenceConstructor
    public Sale() {
		
    }

	@JsonCreator
    public Sale(
            @JsonProperty("client") Client client, 
            @JsonProperty("seller") Seller seller, 
            @JsonProperty("saleItem") ArrayList<SaleItem> saleItem, 
            @JsonProperty("paymentMethod") Integer paymentMethod,
            @JsonProperty("saleDate") Date saleDate) {
		this.client = client;
		this.seller = seller;
		this.saleItem = saleItem;
		this.paymentMethod = paymentMethod;
		this.saleDate = saleDate;
    }

	public String getSaleNumber() {
		return saleNumber;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	

	public ArrayList<SaleItem> getSaleItem() {
		return saleItem;
	}

	public void setSaleItem(ArrayList<SaleItem> saleItem) {
		this.saleItem = saleItem;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public String getPaymentMethodConverted() {
		if(paymentMethod == 1) {
			return "Pagto á Vista";
		}else {
			return "Pagto a prazo – cheque pré-datado";
		}
	}
	
	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	@Override
	public String toString() {
		return "Sale [saleNumber=" + saleNumber + ", client=" + client + ", seller=" + seller + ", purchaseItem="
				+ saleItem + ", paymentMethod=" + paymentMethod + ", saleDate=" + saleDate + "]";
	}

}
