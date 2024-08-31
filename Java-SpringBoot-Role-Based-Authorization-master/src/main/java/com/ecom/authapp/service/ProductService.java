package com.ecom.authapp.service;

import java.util.List;
import java.util.Optional;

import com.ecom.authapp.model.Product;

public interface ProductService {

	List<Product> getAll();

	Product addProduct(Product product);

	Optional<Product> getProductById(Integer productId);

	void deleteProduct(Integer productId);

	Product updateProduct(Product product);

	List<Product> searchProduct(String searchKeyword);

}
