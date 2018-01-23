package com.stocks.model;

import java.io.Serializable;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Document
@JsonIgnoreProperties(ignoreUnknown=true)
public class Stock implements Serializable {
	
	public Stock() {
		super();
	}

	public Stock(String name, Double currentPrice) {
		super();
		this.name = name;
		this.currentPrice = currentPrice;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	
	@NotEmpty(message="Name is required")
	@Size(max=50, message = "Name can be max 50 characters")
	private String name;
	
	@NotNull(message ="Price is required")
	@DecimalMin("0.0") 
	private Double currentPrice;
	
	@JsonProperty(access = Access.READ_ONLY)
	private Long createdAt;
	
	@JsonProperty(access = Access.READ_ONLY)
	private Long updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}
	

}
