package com.ecom.authapp.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.authapp.model.Cart;
import com.ecom.authapp.service.CartService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/cart")
public class CartController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

	@Autowired
	CartService cartService;

	@GetMapping("/getAll")
	public ResponseEntity<List<Cart>> getAll() {
		try {
			LOGGER.info("CartController :: getAll method : Start()");
			List<Cart> cartList = cartService.getAll();

			if (!cartList.isEmpty()) {
				return new ResponseEntity<>(cartList, HttpStatus.OK);
			} else {
				LOGGER.error("CartController :: getAll method : End() : cart list is null");
				return new ResponseEntity<>(cartList, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			LOGGER.error("CartController :: getAll method : Exception() : ", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/addToCart")
	public ResponseEntity<Object> addToCart(@RequestBody Cart cart) {

		try {
			LOGGER.info(" Rest controller Implementation- addToCart() method:Start()");
			Cart addedToCart = this.cartService.addToCart(cart);
			LOGGER.info(" Rest controller Implementation- addToCart() method:End()");
			return ResponseEntity.ok().body(addedToCart);

		} catch (Exception e) {
			LOGGER.info(" Rest controller Implementation- addToCart() Exception: %s ", e);
			return ResponseEntity.ok().body(HttpStatus.NO_CONTENT);
		}

	}

	@DeleteMapping("/deleteCart/{cartId}")
	public Map<String, String> deleteCart(@PathVariable("cartId") Integer cartId) {

		try {
			LOGGER.info("Rest controller Implementation- deleteCart() method:Start()");
			Optional<Cart> getCart = this.cartService.getCartById(cartId);

			if (!getCart.isEmpty()) {
				this.cartService.deleteCart(cartId);
				LOGGER.info(" deleteProduct() method:End()");
				return Collections.singletonMap("success", "Record deleted Successfully");

			} else {
				LOGGER.info("Rest controller Implementation- deleteCart() method:End()");
				return Collections.singletonMap("Failed", "Record failed to delete. error occurs");
			}
		} catch (Exception e) {
			LOGGER.info("delete Cart id error occured:%s", e);
			return Collections.singletonMap("Failed", "INTERNAL_SERVER_ERROR");
		}
	}

	@GetMapping("/getCartByUserId/{userId}")
	public ResponseEntity<List<Cart>> getCartByUserId(@PathVariable("userId") Integer userId) {
		try {
			LOGGER.info("Cart Controller Rest controller Implementation-getCartByUserId method:Start()");
			List<Cart> cartList = this.cartService.getCartByUserId(userId);

			if (!cartList.isEmpty()) {
				LOGGER.info("Cart Controller  Rest controller Implementation-getCartByUserId method:End()");
				return new ResponseEntity<>(cartList, HttpStatus.OK);
			} else {
				LOGGER.error("Cart Controller  Rest controller Implementation- getCartByUserId method EMP list is null:End()");
				return new ResponseEntity<>(cartList, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			LOGGER.error("Cart Controller  getCartByUserId error occured %s", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
