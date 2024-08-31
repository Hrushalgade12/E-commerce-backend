package com.ecom.authapp.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.authapp.model.Product;
import com.ecom.authapp.repository.ProductDao;
import com.ecom.authapp.service.ProductService;

@Service
public class ProductServiceimpl implements ProductService{
	@Autowired
	ProductDao productDao;

	@Override
	public List<Product> getAll() {
		return productDao.findAll();
	}

	@Override
	public Product addProduct(Product product) {
		return productDao.save(product);
	}

	@Override
	public Optional<Product> getProductById(Integer productId) {
		return productDao.findById(productId);
	}

	@Override
	public void deleteProduct(Integer productId) {
		productDao.deleteById(productId);
		
	}

	@Override
	public Product updateProduct(Product product) {
		return productDao.save(product);
	}

	@Override
	public List<Product> searchProduct(String searchKeyword) {
		return productDao.searchProduct(searchKeyword);
	}

	
}
