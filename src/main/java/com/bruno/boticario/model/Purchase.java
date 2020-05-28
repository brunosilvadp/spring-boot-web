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
@Document("purchases")
public class Purchase {

	@Id
	private String purchaseNumber;
	@DBRef
	private Provider provider;
	@DBRef
	private ArrayList<PurchaseItem> purchaseItem;
	@Field("purchase_date")
	private Date purchaseDate;
	
	public Purchase(Provider provider, ArrayList<PurchaseItem> purchaseItem) {
		this.provider = provider;
		this.purchaseItem = purchaseItem;
	}
	
	@PersistenceConstructor
    public Purchase() {
		
    }

	@JsonCreator
    public Purchase(
            @JsonProperty("provider") Provider provider, 
            @JsonProperty("purchaseItem") ArrayList<PurchaseItem> purchaseItem, 
            @JsonProperty("purchaseDate") Date purchaseDate) {
		this.provider = provider;
		this.purchaseItem = purchaseItem;
		this.purchaseDate = purchaseDate;
    }

	public String getPurchaseNumber() {
		return purchaseNumber;
	}


	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public ArrayList<PurchaseItem> getPurchaseItem() {
		return purchaseItem;
	}

	public void setPurchaseItem(ArrayList<PurchaseItem> purchaseItem) {
		this.purchaseItem = purchaseItem;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	@Override
	public String toString() {
		return "Purchase [purchaseNumber=" + purchaseNumber + ", provider=" + provider + ", purchaseDate="
				+ purchaseDate + "]";
	}
	
	
}
