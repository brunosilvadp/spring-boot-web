package com.bruno.boticario.model;

import org.springframework.data.annotation.PersistenceConstructor;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Bruno Silva
 *
 */
@Entity
@Table(name = "providers")
public class Provider extends Person {
	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String cnpj;
	@Column(name = "contact_name")
	private String contactName;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "provider_id", referencedColumnName = "id")
	private List<Purchase> saleList;

	public Provider(String name, String phone, String email, String cnpj,
			String contactName) {
		super(name, phone, email);
		this.cnpj = cnpj;
		this.contactName = contactName;
	}

	@PersistenceConstructor
    public Provider() {
		
    }
	
	@JsonCreator
    public Provider(
    		@JsonProperty("id") Long id,
    		@JsonProperty("name") String name, 
            @JsonProperty("phone") String phone, 
            @JsonProperty("email") String email, 
            @JsonProperty("cnpj") String cnpj,
            @JsonProperty("contactName") String contactName) {
        super(name, phone, email);
		this.id = id;
        this.cnpj = cnpj;
		this.contactName = contactName;
    }
	
	public Long getId() {
		return id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Override
	public String toString() {
		return "Provider [cnpj=" + cnpj + ", contactName=" + contactName + "]";
	}

}
