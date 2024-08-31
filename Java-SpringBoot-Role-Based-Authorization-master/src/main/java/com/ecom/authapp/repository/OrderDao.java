package com.ecom.authapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecom.authapp.model.Order;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {

}
