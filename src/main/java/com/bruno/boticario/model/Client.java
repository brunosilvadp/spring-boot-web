package com.bruno.boticario.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
/**
 * @author Bruno Silva
 *
 */
@Document("clients")
@JsonTypeName("client")
public class Client extends Person{
	@Indexed(unique=true)
	private String cpf;
	@Field("credit_limit")
	private Double creditLimit;

	public Client(String name, String phone, String email, String cpf,
			Double creditLimit) {
		super(name, phone, email);
		this.cpf = cpf;
		this.creditLimit = creditLimit;
	}
	@JsonCreator
    public Client(
    		@JsonProperty("code") String code,
    		@JsonProperty("name") String name, 
            @JsonProperty("phone") String phone, 
            @JsonProperty("email") String email, 
            @JsonProperty("cpf") String cpf,
            @JsonProperty("creditLimit") Double creditLimit) {
        super(code, name, phone, email);
        this.cpf = cpf;
		this.creditLimit = creditLimit;
    }

	public Client() {
		super();
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
