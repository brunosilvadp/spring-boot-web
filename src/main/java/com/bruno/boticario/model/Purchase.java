package com.bruno.boticario.model;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.PersistenceConstructor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Bruno Silva
 *
 */
@Entity
@Table(name = "purchases")
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	private Provider provider;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "purchase_id", referencedColumnName = "id")
	private List<PurchaseItem> purchaseItem;
	private Double total;
	@Column(name = "purchase_date")
	@Temporal(TemporalType.DATE)
	private Date purchaseDate;
	
	public Purchase(final Provider provider, final List<PurchaseItem> purchaseItem) {
		this.provider = provider;
		this.purchaseItem = purchaseItem;
	}
	
	@PersistenceConstructor
    public Purchase() {
		
    }

	@JsonCreator
    public Purchase(
            @JsonProperty("provider") final Provider provider, 
            @JsonProperty("purchaseItem") final ArrayList<PurchaseItem> purchaseItem, 
            @JsonProperty("purchaseDate") final Date purchaseDate) {
		this.provider = provider;
		this.purchaseItem = purchaseItem;
		this.purchaseDate = purchaseDate;
    }

	public Long getPurchaseNumber() {
		return id;
	}


	public Provider getProvider() {
		return provider;
	}

	public void setProvider(final Provider provider) {
		this.provider = provider;
	}

	public List<PurchaseItem> getPurchaseItem() {
		return purchaseItem;
	}

	public void setPurchaseItem(final List<PurchaseItem> purchaseItem) {
		this.purchaseItem = purchaseItem;
	}

	public String getPurchaseDate() {
		return new SimpleDateFormat("dd/MM/yyyy").format(purchaseDate);
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	public String getTotal() {
		DecimalFormat df = new DecimalFormat("#,##0.00");
		return "R$ " + df.format(this.total);
	}

	public void setPurchaseDate(final Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	@Override
	public String toString() {
		return "Purchase [purchaseNumber=" + id + ", provider=" + provider + ", purchaseDate="
				+ purchaseDate + "]";
	}
	
	
}
