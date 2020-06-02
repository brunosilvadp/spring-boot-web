package com.bruno.boticario.model;

import java.util.List;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.PersistenceConstructor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Bruno Silva
 *
 */
@Entity
@Table(name = "sales")
public class Sale {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	private Client client;
	@ManyToOne
	private Seller seller;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "sale_id", referencedColumnName = "id")
	private List<SaleItem> saleItem;
	@Column(name = "payment_method")
	private Integer paymentMethod;
	@Column(name = "sale_date")
	private Date saleDate;

	public Sale(Client client, Seller seller, List<SaleItem> saleItem,
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
            @JsonProperty("saleItem") List<SaleItem> saleItem, 
            @JsonProperty("paymentMethod") Integer paymentMethod,
            @JsonProperty("saleDate") Date saleDate) {
		this.client = client;
		this.seller = seller;
		this.saleItem = saleItem;
		this.paymentMethod = paymentMethod;
		this.saleDate = saleDate;
    }

	public Long getId() {
		return id;
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

	

	public List<SaleItem> getSaleItem() {
		return saleItem;
	}

	public void setSaleItem(List<SaleItem> saleItem) {
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
		return "Sale [saleNumber=" + id + ", client=" + client + ", seller=" + seller + ", purchaseItem="
				+ saleItem + ", paymentMethod=" + paymentMethod + ", saleDate=" + saleDate + "]";
	}

}
