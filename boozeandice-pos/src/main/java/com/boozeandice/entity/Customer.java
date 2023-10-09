package com.boozeandice.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

@Entity
@Table(name="customer")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable = false, unique=true)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email_address")
	private String emailAddress;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	@OneToOne
	@JoinColumn(name="created_by")
	private User user;
	
	@Column(name="created_date")
	private Date createdDate;
		
	//@Column(name="img_location")
	//private String profilePicture;
	
	public Customer() {
		
	}
	
	public Customer(User user) {
		this.user = user;
		this.createdDate = new Date(System.currentTimeMillis());
	}
	
	@OneToMany(mappedBy = "customer")
	private List<Transaction> transaction;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public List<Transaction> getTransaction() {
		return transaction;
	}
	
	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	
	
	
	
	
}
