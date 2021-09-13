package com.devcamp.shop24h.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comments")
public class Comments {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty(message = "name can't be empty")
	@Column(name = "name", nullable = false)
	private String name;

	@NotEmpty(message = "comments can't be empty")
	@Column(name = "comments", nullable = false)
	private String comments;

	@Column(name = "rate_star")
	private java.math.BigDecimal rateStar;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "productId")
	private Product productId;

	public Comments(int id, @NotEmpty(message = "name can't be empty") String name,
			@NotEmpty(message = "comments can't be empty") String comments, BigDecimal rateStar, Product productId) {
		super();
		this.id = id;
		this.name = name;
		this.comments = comments;
		this.rateStar = rateStar;
		this.productId = productId;
	}

	public Comments() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Product getProductId() {
		return productId;
	}

	public void setProductId(Product productId) {
		this.productId = productId;
	}

	public java.math.BigDecimal getRateStar() {
		return rateStar;
	}

	public void setRateStar(java.math.BigDecimal rateStar) {
		this.rateStar = rateStar;
	}

}
