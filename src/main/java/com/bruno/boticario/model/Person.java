package com.bruno.boticario.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import org.hibernate.annotations.CreationTimestamp;

/**
 * @author Bruno Silva
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
	@JsonSubTypes.Type(value = Client.class, name = "client"),
	@JsonSubTypes.Type(value = Seller.class, name = "seller"),
	@JsonSubTypes.Type(value = Provider.class, name = "provider")
})
@MappedSuperclass
public abstract class Person implements Comparable<Person>{
	@Column(name = "name")
	private String name;
	@Column(name = "phone")
	private String phone;
	@Column(name = "email")
	private String email;
	@Column(name = "created_at")
	@CreationTimestamp
	private Date registerDate;

	public Person(String name, String phone, String email) {
		this.name = name;
		this.phone = phone;
		this.email = email;
	}
	
	public Person() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", phone=" + phone + ", email=" + email + ", registerDate="
				+ registerDate + "]";
	}
	
	@Override
	public int compareTo(Person o) {
		return this.name.compareTo(o.name);
	}
}
