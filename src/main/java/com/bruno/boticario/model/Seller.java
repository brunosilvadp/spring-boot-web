package com.bruno.boticario.model;

import org.springframework.data.annotation.PersistenceConstructor;
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
@Document("sellers")
@JsonTypeName("seller")
public class Seller extends Person {
	@Indexed(unique=true)
	private String cpf;
	@Field("monthly_goal")
	private Double monthlyGoal;

	public Seller(String name, String phone, String email, String cpf,
			Double monthlyGoal) {
		super(name, phone, email);
		this.cpf = cpf;
		this.monthlyGoal = monthlyGoal;
	}
	
	@PersistenceConstructor
    public Seller() {
		
    }
	
	@JsonCreator
    public Seller(
    		@JsonProperty("code") String code,
    		@JsonProperty("name") String name, 
            @JsonProperty("phone") String phone, 
            @JsonProperty("email") String email, 
            @JsonProperty("cpf") String cpf,
            @JsonProperty("monthlyGoal") Double monthlyGoal) {
        super(code, name, phone, email);
        this.cpf = cpf;
		this.monthlyGoal = monthlyGoal;
    }

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Double getMonthlyGoal() {
		return monthlyGoal;
	}

	public void setMonthlyGoal(Double monthlyGoal) {
		this.monthlyGoal = monthlyGoal;
	}

	@Override
	public String toString() {
		return "Seller [cpf=" + cpf + ", monthlyGoal=" + monthlyGoal + " " + super.toString() + " ]";
	}
}
