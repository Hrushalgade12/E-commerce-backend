package com.ecom.authapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecom.authapp.model.Product;


@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

	@Query(value = "SELECT p FROM Product p WHERE lower(p.name) LIKE lower(concat('%', :searchKeyword,'%')) OR lower(p.color) LIKE lower(concat('%', :searchKeyword,'%')) OR lower(p.catagory) LIKE lower(concat('%', :searchKeyword,'%')) OR lower(p.description) LIKE lower(concat('%', :searchKeyword,'%'))")
	List<Product> searchProduct(@Param("searchKeyword") String searchKeyword);

}
