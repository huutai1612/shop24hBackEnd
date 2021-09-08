package com.devcamp.shop24h.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty(message = "product code can't be empty")
	@Column(name = "product_code", nullable = false, unique = true)
	private String productCode;

	@NotEmpty(message = "product name can't be empty")
	@Column(name = "product_name", nullable = false)
	private String productName;

	@Column(name = "product_description")
	private String productDescription;

	@NotEmpty(message = "product scale can't be empty")
	@Column(name = "product_scale", nullable = false)
	private String productScale;

	@NotEmpty(message = "product's vendor can't be empty")
	@Column(name = "product_vendor", nullable = false)
	private String productVendor;

	@NotNull
	@Column(name = "quantity_in_stock", nullable = false)
	private int quantityInStock;

	@NotNull
	@Column(name = "buy_price", nullable = false)
	private java.math.BigDecimal buyPrice;

	@NotEmpty(message = "url can't be empty")
	@Column(name = "url_image", nullable = false)
	private String urlImage;

	@Column(name = "isRelated")
	private Boolean isRelated;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "product_line_id", nullable = false)
	@JsonIgnore
	private ProductLine productLineId;

	@OneToMany(mappedBy = "productId", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<OrderDetail> orderDetail;

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(int id, @NotEmpty(message = "product code can't be empty") String productCode,
			@NotEmpty(message = "product name can't be empty") String productName, String productDescription,
			@NotEmpty(message = "product scale can't be empty") String productScale,
			@NotEmpty(message = "product's vendor can't be empty") String productVendor, @NotNull int quantityInStock,
			BigDecimal buyPrice, @NotEmpty(message = "url can't be empty") String urlImage, Boolean isRelated,
			ProductLine productLineId, List<OrderDetail> orderDetail) {
		super();
		this.id = id;
		this.productCode = productCode;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productScale = productScale;
		this.productVendor = productVendor;
		this.quantityInStock = quantityInStock;
		this.buyPrice = buyPrice;
		this.urlImage = urlImage;
		this.isRelated = isRelated;
		this.productLineId = productLineId;
		this.orderDetail = orderDetail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public ProductLine getProductLineId() {
		return productLineId;
	}

	public void setProductLineId(ProductLine productLineId) {
		this.productLineId = productLineId;
	}

	public String getProductScale() {
		return productScale;
	}

	public void setProductScale(String productScale) {
		this.productScale = productScale;
	}

	public String getProductVendor() {
		return productVendor;
	}

	public void setProductVendor(String productVendor) {
		this.productVendor = productVendor;
	}

	public int getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public java.math.BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(java.math.BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public List<OrderDetail> getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(List<OrderDetail> orderDetail) {
		this.orderDetail = orderDetail;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public Boolean getIsRelated() {
		return isRelated;
	}

	public void setIsRelated(Boolean isRelated) {
		this.isRelated = isRelated;
	}

}
