package com.ecom.authapp.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.authapp.model.Order;
import com.ecom.authapp.repository.OrderDao;
import com.ecom.authapp.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderDao orderDao;

	@Override
	public List<Order> getAll() {
		return orderDao.findAll();
	}

	@Override
	public Order orderNow(Order order) {
		return orderDao.save(order);
	}

	@Override
	public Optional<Order> getOrderById(Integer orderId) {
		return orderDao.findById(orderId);
	}

	@Override
	public void deleteOrder(Integer orderId) {
		orderDao.deleteById(orderId);
	}

}
