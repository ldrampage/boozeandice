package com.boozeandice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "product")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "price", nullable = false)
	private Double price;

	@Column(name = "createdDate", nullable = false)
	private Date createdDate;

	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_category_id")
	private ProductCategory productCategory;

	@Column(name = "manufacturer")
	private String manufacturer;

	@Column(name = "supplier")
	private String supplier;

	@OneToMany(mappedBy = "product")
	private List<ProductStock> productStock;
	
	@Transient
	private Long overallStock;
	
	public Long getOverallStock() {
		return overallStock;
	}

	public void setOverallStock(Long overallStock) {
		this.overallStock = overallStock;
	}

	public List<ProductStock> getProductStock() {
		return productStock;
	}

	public void setProductStock(List<ProductStock> productStock) {
		this.productStock = productStock;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public ProductCategory getProductCategory() {
		if(productCategory == null) {
			return new ProductCategory();
		}
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	
}
