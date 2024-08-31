package com.ecom.authapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.authapp.model.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer>{
	Optional<RefreshToken> findByToken(String token);
}
