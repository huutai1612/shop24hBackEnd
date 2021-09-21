package com.devcamp.shop24h.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comments")
public class Comments {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "comments")
	private String comments;

	@Column(name = "rate_star")
	private java.math.BigDecimal rateStar;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "productId")
	private Product productId;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "customerId")
	private Customer customerId;

	@OneToMany(mappedBy = "commentId", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	List<ReplyComment> replies;

	public Comments(int id, String name, String comments, BigDecimal rateStar, Product productId, Customer customerId) {
		super();
		this.id = id;
		this.name = name;
		this.comments = comments;
		this.rateStar = rateStar;
		this.productId = productId;
		this.customerId = customerId;
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

	/**
	 * @return the customerId
	 */
	public Customer getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the replies
	 */
	public List<ReplyComment> getReplies() {
		return replies;
	}

	/**
	 * @param replies the replies to set
	 */
	public void setReplies(List<ReplyComment> replies) {
		this.replies = replies;
	}

}
