package com.ecom.authapp.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.authapp.model.Cart;
import com.ecom.authapp.repository.CartDao;
import com.ecom.authapp.service.CartService;

@Service
public class CartServiceImpl implements CartService {
	@Autowired
	CartDao cartDao;

	@Override
	public List<Cart> getAll() {
		return cartDao.findAll();
	}

	@Override
	public Cart addToCart(Cart cart) {
		return cartDao.save(cart);
	}

	@Override
	public Optional<Cart> getCartById(Integer cartId) {
		return cartDao.findById(cartId);
	}

	@Override
	public void deleteCart(Integer cartId) {
		cartDao.deleteById(cartId);
	}

	
	@Override
	public List<Cart> getCartByUserId(Integer userId) {
		return cartDao.getCartByUserId(userId);
	}

}
