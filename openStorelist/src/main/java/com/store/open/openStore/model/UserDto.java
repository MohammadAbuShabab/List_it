package com.store.open.openStore.model;

import java.util.List;

import org.springframework.lang.Nullable;

import com.store.open.openStore.model.enums.USER_ROLE;

public class UserDto {
	private Long id;
	
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String username;
	@Nullable
	private List<String> roles;
	private String password;
	private String userPicture;
	
	
	public UserDto(Long id, String firstName, String lastName, String phoneNumber, String email, String username,
			List<String> roles, String password, String userPicture) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.username = username;
		this.roles = roles;
		this.password = password;
		this.userPicture = userPicture;
	}
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public String getUserPicture() {
		return userPicture;
	}
	public void setUserPicture(String userPicture) {
		this.userPicture = userPicture;
	}
	
}
