package com.ecom.authapp.service;

import com.ecom.authapp.model.RegisterDto;
import com.ecom.authapp.model.User;
import com.ecom.authapp.model.UserDetailsDto;
import com.ecom.authapp.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new UserDetailsDto(user);
    }
    public void  registerUser(RegisterDto registerDto){
        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(registerDto.registerDtoToUser(registerDto));
    }
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}
}