package com.bruno.boticario.model;

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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
/**
 * @author Bruno Silva
 *
 */
@JsonTypeName("client")
@Entity
@Table(name = "clients")
public class Client extends Person{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String cpf;
	@Column(name = "credit_limit")
	private Double creditLimit;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "client_id", referencedColumnName = "id")
	private List<Sale> saleList;

	public Client(String name, String phone, String email, String cpf,
			Double creditLimit) {
		super(name, phone, email);
		this.cpf = cpf;
		this.creditLimit = creditLimit;
	}
	@JsonCreator
    public Client(
    		@JsonProperty("id") Long id,
    		@JsonProperty("name") String name, 
            @JsonProperty("phone") String phone, 
            @JsonProperty("email") String email, 
            @JsonProperty("cpf") String cpf,
            @JsonProperty("creditLimit") Double creditLimit) {
        super(name, phone, email);
		this.id = id;
        this.cpf = cpf;
		this.creditLimit = creditLimit;
    }

	public Client() {
		super();
	}

	public Long getId() {
		return id;
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}

	@Override
	public String toString() {
		return "Client [cpf=" + cpf + ", creditLimit=" + creditLimit + " " + super.toString() + "]";
	}
}
