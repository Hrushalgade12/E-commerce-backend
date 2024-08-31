package com.ecom.authapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecom.authapp.model.Cart;

@Repository
public interface CartDao extends JpaRepository<Cart, Integer> {

	@Query(value = "SELECT c FROM Cart c WHERE c.userId=userId")
	List<Cart> getCartByUserId( Integer userId);

}
