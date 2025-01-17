package com.store.open.openStore.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private @NotNull String name;
    @OneToMany(mappedBy="product") 
    private List<ProductPicture> attachments;

    private @NotNull double price;
    private @NotNull String description;
    private @NotNull Boolean availability;
    private @NotNull Integer quantityAvailable = 1;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    Category category;
    
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    User owner;
    
    private String dateAdded = Instant.now().toString();
    
    public Product() {
        super();
    }
    
    


	public Product(Integer id, @NotNull String name, @NotNull double price,
			@NotNull String description, @NotNull Boolean availability, @NotNull Integer quantityAvailable) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.availability = availability;
		this.quantityAvailable = quantityAvailable;
	}
	
	public Product(@NotNull String name, @NotNull double price,
			@NotNull String description, @NotNull Boolean availability, @NotNull Integer quantityAvailable) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
		this.availability = availability;
		this.quantityAvailable = quantityAvailable;
	}
	

	public Product(Integer id, @NotNull String name, List<ProductPicture> attachments, @NotNull double price,
			@NotNull String description, @NotNull Boolean availability, @NotNull Integer quantityAvailable,
			Category category, User owner) {
		super();
		this.id = id;
		this.name = name;
		this.attachments = attachments;
		this.price = price;
		this.description = description;
		this.availability = availability;
		this.quantityAvailable = quantityAvailable;
		this.category = category;
		this.owner = owner;
	}




	public User getOwner() {
		return owner;
	}




	public void setOwner(User owner) {
		this.owner = owner;
	}




	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}



	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public Boolean getAvailability() {
		return availability;
	}


	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}


	public Integer getQuantityAvailable() {
		return quantityAvailable;
	}


	public void setQuantityAvailable(Integer quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}




	public List<ProductPicture> getAttachments() {
		return attachments;
	}




	public void setAttachments(List<ProductPicture> attachments) {
		this.attachments = attachments;
	}




	public String getDateAdded() {
		return dateAdded;
	}




	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}
	
	
	
    
    
}