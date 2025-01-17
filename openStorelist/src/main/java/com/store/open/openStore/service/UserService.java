package com.store.open.openStore.service;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.store.open.openStore.model.User;
import com.store.open.openStore.model.UserDto;


public interface UserService extends UserDetailsService{
 User save(UserDto registrationDto);
 User findUserByEmail(String email);
}