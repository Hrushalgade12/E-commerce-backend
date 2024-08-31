package com.ecom.authapp.service;

import java.util.List;
import java.util.Optional;

import com.ecom.authapp.model.Cart;

public interface CartService {
	List<Cart> getAll();

	Cart addToCart(Cart cart);

	Optional<Cart> getCartById(Integer cartId);

	void deleteCart(Integer cartId);

	List<Cart> getCartByUserId(Integer userId);
}
