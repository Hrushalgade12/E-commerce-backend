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

import com.ecom.authapp.model.Order;
import com.ecom.authapp.service.OrderService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/order")
public class OrderController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	OrderService orderService;

	@GetMapping("/getAll")
	public ResponseEntity<List<Order>> getAll() {
		try {
			LOGGER.info("OrderController Rest controller Implementation-getAll method:Start()");
			List<Order> orderList = orderService.getAll();

			if (!orderList.isEmpty()) {
				LOGGER.info("OrderController Rest controller Implementation-getAll method:End()");
				return new ResponseEntity<>(orderList, HttpStatus.OK);
			} else {
				LOGGER.error("OrderController Rest controller Implementation- getAll method cart list is null:End()");
				return new ResponseEntity<>(orderList, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			LOGGER.error("OrderController get all error occured %s", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	@PostMapping("/orderNow")
	public ResponseEntity<Object> orderNow(@RequestBody Order order) {

		try {
			LOGGER.info(" Rest controller Implementation- orderNow() method:Start()");
			Order addedOrder = this.orderService.orderNow(order);
			LOGGER.info(" Rest controller Implementation- orderNow() method:End()");
			return ResponseEntity.ok().body(addedOrder);

		} catch (Exception e) {
			LOGGER.info(" Rest controller Implementation- orderNow() Exception: %s ", e);
			return ResponseEntity.ok().body(HttpStatus.NO_CONTENT);
		}

	}
	@DeleteMapping("/deleteOrder/{orderId}")
	public Map<String, String> deleteOrder(@PathVariable("orderId") Integer orderId) {

		try {
			LOGGER.info("Rest controller Implementation- deleteOrder() method:Start()");
			Optional<Order> getOrder =this.orderService.getOrderById(orderId);

			if (!getOrder.isEmpty()) {
				this.orderService.deleteOrder(orderId);
				LOGGER.info(" deleteOrder() method:End()");
				return Collections.singletonMap("success", "Record deleted Successfully");

			} else {
				LOGGER.info("Rest controller Implementation- deleteOrder() method:End()");
				return Collections.singletonMap("Failed", "Record failed to delete. error occurs");
			}
		} catch (Exception e) {
			LOGGER.info("deleteOrder id error occured:%s", e);
			return Collections.singletonMap("Failed", "INTERNAL_SERVER_ERROR");
		}
	}
}
