package com.ecom.authapp.service;

import java.util.List;
import java.util.Optional;

import com.ecom.authapp.model.Order;

public interface OrderService {

	List<Order> getAll();

	Order orderNow(Order order);

	Optional<Order> getOrderById(Integer orderId);

	void deleteOrder(Integer orderId);



}
