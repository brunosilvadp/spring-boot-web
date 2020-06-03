package com.bruno.boticario.model;

import org.springframework.data.annotation.PersistenceConstructor;

import java.text.DecimalFormat;
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
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * @author Bruno Silva
 *
 */
@JsonTypeName("seller")
@Entity
@Table(name = "sellers")
public class Seller extends Person {
	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String cpf;
	@Column(name = "monthly_goal")
	private Double monthlyGoal;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "seller_id", referencedColumnName = "id")
	private List<Sale> saleList;

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
    		@JsonProperty("id") Long id,
    		@JsonProperty("name") String name, 
            @JsonProperty("phone") String phone, 
            @JsonProperty("email") String email, 
            @JsonProperty("cpf") String cpf,
            @JsonProperty("monthlyGoal") Double monthlyGoal) {
        super(name, phone, email);
		this.id = id;
        this.cpf = cpf;
		this.monthlyGoal = monthlyGoal;
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

	public Double getMonthlyGoal() {
		return monthlyGoal;
	}

	public String getMonthlyGoalFormated(){
		DecimalFormat df = new DecimalFormat("#,##0.00");
		return "R$ " + df.format(monthlyGoal);	
	}

	public void setMonthlyGoal(Double monthlyGoal) {
		this.monthlyGoal = monthlyGoal;
	}

	@Override
	public String toString() {
		return "Seller [cpf=" + cpf + ", monthlyGoal=" + monthlyGoal + " " + super.toString() + " ]";
	}
}
