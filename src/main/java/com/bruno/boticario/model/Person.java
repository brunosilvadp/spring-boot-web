package com.bruno.boticario.model;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

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
public abstract class Person implements Comparable<Person>{
	@Id
	@JsonIgnore
	private String code;
	private String name;
	private String phone;
	private String email;
	@Field("created_at")
	private DateTime registerDate;

	public Person(String name, String phone, String email) {
		this.name = name;
		this.phone = phone;
		this.email = email;
	}
	
	public Person(String code, String name, String phone, String email) {
		this.code = code;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}
	
	public Person() {
	}

	public String getCode() {
		return code;
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

	public DateTime getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(DateTime registerDate) {
		this.registerDate = registerDate;
	}

	@Override
	public String toString() {
		return "Person [code=" + code + ", name=" + name + ", phone=" + phone + ", email=" + email + ", registerDate="
				+ registerDate + "]";
	}
	
	@Override
	public int compareTo(Person o) {
		return this.name.compareTo(o.name);
	}
}
