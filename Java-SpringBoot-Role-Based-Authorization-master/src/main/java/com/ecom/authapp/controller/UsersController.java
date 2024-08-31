package com.ecom.authapp.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.authapp.auth.TokenManager;
import com.ecom.authapp.model.JwtResponseDTO;
import com.ecom.authapp.model.LoginDto;
import com.ecom.authapp.model.Product;
import com.ecom.authapp.model.RefreshToken;
import com.ecom.authapp.model.RefreshTokenRequestDTO;
import com.ecom.authapp.model.RegisterDto;
import com.ecom.authapp.model.Role;
import com.ecom.authapp.model.User;
import com.ecom.authapp.service.RefreshTokenService;
import com.ecom.authapp.service.UserDetailsServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/")
@RestController
public class UsersController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
	private AuthenticationManager authenticationManager;
	private TokenManager tokenManager;
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	RefreshTokenService refreshTokenService;

	@Autowired
	public UsersController(AuthenticationManager authenticationManager, TokenManager tokenManager) {
		this.authenticationManager = authenticationManager;
		this.tokenManager = tokenManager;

	}

	@PostMapping("login")
	public JwtResponseDTO login(@RequestBody LoginDto loginDto) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
		final String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		String name = authentication.getName();
		Set<String> collectedRoles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
		if (authentication.isAuthenticated()) {
			RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginDto.getUsername());
			
			return JwtResponseDTO.builder().name(name).accessToken(tokenManager.generateToken(authorities, authentication.getName()))
					.token(refreshToken.getToken()).roles(collectedRoles).build();
		} else {
			throw new UsernameNotFoundException("invalid user request..!!");
		}

	}

	@PostMapping("refreshToken")
	public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
		try {
			RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequestDTO.getToken()).get();
			User userInfo = refreshToken.getUserInfo();
			String roles = userInfo.getRoles().stream().map(role -> role.getName()).collect(Collectors.joining(","));
			Set<String> collectedRoles =  userInfo.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet());
			String accessToken = tokenManager.generateToken(roles, userInfo.getUsername());
			return JwtResponseDTO.builder().name(userInfo.getFirstName()).accessToken(accessToken).token(refreshTokenRequestDTO.getToken()).roles(collectedRoles).build();
		} catch (Exception e) {
			System.out.println("Refresh Token is not in DB..!!");
			e.printStackTrace();
			return new JwtResponseDTO();
		}
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			LOGGER.info("UsersController Rest controller Implementation-getAllUsers method:Start()");
			List<User> usersList = userDetailsService.getAllUsers();
			Set<Role> roles = usersList.get(0).getRoles();

			if (!usersList.isEmpty()) {
				LOGGER.info("UsersController Rest controller Implementation-getAllUsers method:End()");
				return new ResponseEntity<>(usersList, HttpStatus.OK);
			} else {
				LOGGER.error("UsersController Rest controller Implementation- getAllUsers method EMP list is null:End()");
				return new ResponseEntity<>(usersList, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			LOGGER.error("UsersController getAllUsers error occured %s", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@PostMapping("register")
	public ResponseEntity register(@RequestBody RegisterDto registerDto) {
		userDetailsService.registerUser(registerDto);
		return ResponseEntity.ok("Added Successfully");
	}

	@GetMapping("user")
	public String homePage() {
		return "Hello, this user";
	}

	@GetMapping("admin")
	public String newPage() {
		return "Hello, this is admin";
	}

}
