package com.boozeandice.local.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	@Column(name = "notes")
	private String notes;

	@Column(name = "price", nullable = false)
	private Double price;
	
	@Column(name="packaging_fee")
	private Double packagingFee;

	@Column(name = "cost", nullable = false)
	private Double cost;

	@Column(name = "created_date", nullable = false)
	private Date createdDate;
	
	@Column(name = "manufacturer")
	private String manufacturer;

	@Column(name = "supplier")
	private String supplier;

	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;

	@ManyToOne
	@JoinColumn(name = "product_category_id")
	private ProductCategory productCategory;

	@OneToMany(mappedBy = "product")
	private List<ProductStock> productStock;
	
	@Column(name = "stock_available")
	private Long stocks = Long.valueOf(0);

	@Column(name = "img_location")
	private String imgLocation;

	@Transient
	private Long qtyToPurchase;
	
	@Transient
	private String barcodeDigits;

	public Long getQtyToPurchase() {
		if (qtyToPurchase == null) {
			qtyToPurchase = Long.valueOf(1);
		}
		return qtyToPurchase;
	}

	public void setQtyToPurchase(Long qtyToPurchase) {
		this.qtyToPurchase = qtyToPurchase;
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
		if (productCategory == null) {
			return new ProductCategory();
		}
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public String getImgLocation() {
		return imgLocation;
	}

	public void setImgLocation(String imgLocation) {
		this.imgLocation = imgLocation;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Double getPackagingFee() {
		return packagingFee;
	}

	public void setPackagingFee(Double packagingFee) {
		this.packagingFee = packagingFee;
	}
	
	public Long getStocks() {
		return stocks;
	}

	public void setStocks(Long stocks) {
		this.stocks = stocks;
	}
	
	public String getBarcodeDigits() {
		return barcodeDigits;
	}

	public void setBarcodeDigits(String barcodeDigits) {
		this.barcodeDigits = barcodeDigits;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}

}
