package com.store.open.openStore.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class ProductDto {
	
	
	public ProductDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	private Integer id;
	private @NotNull String name;
	private List<String> imagesData = new ArrayList<>();
	private @NotNull double price;
	private @NotNull String description;
	private @NotNull Integer categoryId;
	private Integer quantityAvailable = 1;
	private Long ownerId;
	private String dateAdded;
	private UserDto owner;
	
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
	
	
	public List<String> getImagesData() {
		return imagesData;
	}
	public void setImagesData(List<String> imagesData) {
		this.imagesData = imagesData;
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
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getQuantityAvailable() {
		return quantityAvailable;
	}
	public void setQuantityAvailable(Integer quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	
	public UserDto getOwner() {
		return owner;
	}
	public void setOwner(UserDto owner) {
		this.owner = owner;
	}
	public String getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}
	
	
	
	
}
