package com.store.open.openStore.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.open.openStore.model.CategoryDto;
import com.store.open.openStore.model.Role;
import com.store.open.openStore.model.User;
import com.store.open.openStore.model.UserDto;
import com.store.open.openStore.model.UserPicture;
import com.store.open.openStore.model.enums.USER_ROLE;
import com.store.open.openStore.repository.UserPictureRepository;
import com.store.open.openStore.repository.UserRepository;
import com.store.open.openStore.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserPictureRepository userPictureRepository;
	
	
	
	@PostMapping("/create")
	public ResponseEntity<Boolean> createCategory(@Valid @RequestBody UserDto userDto) {
		userService.save(userDto);
		return new ResponseEntity<>(Boolean.TRUE, HttpStatus.CREATED);
	}
	
	@GetMapping("/info")
	public UserDto getUserInfo(Principal principal) throws Exception {
		String email = principal.getName();
		User user = userService.findUserByEmail(email);
		String picture = user.getAttachment() != null ? user.getAttachment().getFile() : null; 
        
        List<String> roles = ((List<Role>) user.getRoles()).stream().map(Role::getName).collect(Collectors.toList());
		UserDto userDto = new UserDto(user.getId(), user.getFirstName(), user.getLastName(), 
				user.getPhoneNumber(), user.getEmail(), user.getUsername(), 
				roles, null, picture);
		userDto.setPassword(null);
		System.out.println("username returned: " + user.getEmail());
		return userDto;
		
	}
	private User userDtoToUser(UserDto userDto) {
		List<Role> roles = new ArrayList();
		for (String roleDto : userDto.getRoles()) {
			roles.add(new Role("ROLE_"+ roleDto));
		}
		
		User user = new User(userDto.getFirstName(), userDto.getLastName(), userDto.getPhoneNumber(), userDto.getEmail(), userDto.getUsername(), 
				
				 userDto.getPassword(), roles); 
		return user;
	}
	
	/*
	private UserDto userToUserDto(User user) {
		List<String> roles = ((List<Role>) user.getRoles()).stream().map(Role::getName).collect(Collectors.toList());
		UserDto userDto = new UserDto(user.getId(), user.getFirstName() , user.getLastName(), user.getPhoneNumber(), user.getEmail(), user.getUsername(),roles , user.getPassword());
		return userDto;
		
		
	}
	*/

}
