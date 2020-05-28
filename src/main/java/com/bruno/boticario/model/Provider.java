package com.bruno.boticario.model;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Bruno Silva
 *
 */
@Document("providers")
public class Provider extends Person {
	@Indexed(unique=true)
	private String cnpj;
	private String contactName;

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
    		@JsonProperty("code") String code,
    		@JsonProperty("name") String name, 
            @JsonProperty("phone") String phone, 
            @JsonProperty("email") String email, 
            @JsonProperty("cnpj") String cnpj,
            @JsonProperty("contactName") String contactName) {
        super(code, name, phone, email);
        this.cnpj = cnpj;
		this.contactName = contactName;
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
