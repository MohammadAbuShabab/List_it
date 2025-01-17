package com.store.open.openStore.service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.store.open.openStore.model.Role;
import com.store.open.openStore.model.User;
import com.store.open.openStore.model.UserDto;
import com.store.open.openStore.model.UserPicture;
import com.store.open.openStore.model.enums.USER_ROLE;
import com.store.open.openStore.repository.UserPictureRepository;
import com.store.open.openStore.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired 
    UserPictureRepository userPictureRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserDto registrationDto) {
    	List<Role> roles = new ArrayList<Role>();
    	
    	if (registrationDto.getRoles().contains(USER_ROLE.ADMIN.toString())) {
    		roles.add(new Role("ROLE_"+USER_ROLE.BUYER_OR_SELLER.toString())); 
    		roles.add(new Role("ROLE_"+USER_ROLE.ADMIN.toString()));
    		User user = new User(registrationDto.getFirstName(),
	            registrationDto.getLastName(), registrationDto.getPhoneNumber(), registrationDto.getEmail(), registrationDto.getUsername(),
	            passwordEncoder.encode(registrationDto.getPassword()), roles);
    		
    		user = userRepository.save(user);
    		UserPicture picture = new UserPicture();
    		if (registrationDto.getUserPicture() != null) {
    			picture.setFile(registrationDto.getUserPicture());
    			picture.setUser(user);
    			picture = userPictureRepository.save(picture);
    			user.setAttachment(picture);
    		}

	        return userRepository.save(user);

    	} else {
    		roles.add(new Role("ROLE_"+USER_ROLE.BUYER_OR_SELLER.toString()));
    		User user = new User(registrationDto.getFirstName(),
    	            registrationDto.getLastName(), registrationDto.getPhoneNumber(), registrationDto.getEmail(), registrationDto.getUsername(),
    	            passwordEncoder.encode(registrationDto.getPassword()), roles);
    	    	user = userRepository.save(user);
    	    	UserPicture picture = new UserPicture();
    			if (registrationDto.getUserPicture() != null) {
    				picture.setFile(registrationDto.getUserPicture());
    				picture.setUser(user);
    				picture = userPictureRepository.save(picture);
    				user.setAttachment(picture);
    				user = userRepository.save(user);
    			}

    	        return user;
    	}
    	
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection <? extends GrantedAuthority> mapRolesToAuthorities(Collection < Role > roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

	@Override
	public User findUserByEmail(String email) {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(email);
		return user;
	}
}