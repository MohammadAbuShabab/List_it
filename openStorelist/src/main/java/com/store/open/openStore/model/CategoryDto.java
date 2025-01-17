package com.store.open.openStore.model;


import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;



public class CategoryDto {

	
	private Integer id;

	
	private @NotBlank String categoryName;

	private @NotBlank String description;
	
	 
    private @NotBlank String pictureBase64Encoded;


	public CategoryDto() {
	}


	public CategoryDto(Integer id,@NotBlank String categoryName, @NotBlank String description, @NotBlank String pictureBase64Encoded) {
		this.id = id;
		this.categoryName = categoryName;
		this.description = description;
		this.pictureBase64Encoded = pictureBase64Encoded;
		
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "User {category id=" + id + ", category name='" + categoryName + "', description='" + description + "'}";
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getPictureBase64Encoded() {
		return pictureBase64Encoded;
	}


	public void setPictureBase64Encoded(String pictureBase64Encoded) {
		this.pictureBase64Encoded = pictureBase64Encoded;
	}

	
	
}