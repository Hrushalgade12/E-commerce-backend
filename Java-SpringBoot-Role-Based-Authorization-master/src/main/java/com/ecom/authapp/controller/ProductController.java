package com.ecom.authapp.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.security.RolesAllowed;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.authapp.model.Product;
import com.ecom.authapp.service.ProductService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/product")
public class ProductController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
	@Autowired
	ProductService productService;


	@GetMapping("/getAll")
	public ResponseEntity<List<Product>> getAll() {
		try {
			LOGGER.info("ProductController Rest controller Implementation-getAll method:Start()");
			List<Product> productList = productService.getAll();

			if (!productList.isEmpty()) {
				LOGGER.info("ProductController Rest controller Implementation-getAll method:End()");
				return new ResponseEntity<>(productList, HttpStatus.OK);
			} else {
				LOGGER.error("ProductController Rest controller Implementation- getAll method EMP list is null:End()");
				return new ResponseEntity<>(productList, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			LOGGER.error("Product get all error occured %s", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/getProduct/{id}")
	public ResponseEntity<Optional<Product>> getProduct(@PathVariable("id") Integer productId) {
		try {
			LOGGER.info("ProductController Rest controller Implementation-getProduct method:Start()");
			Optional<Product> product = productService.getProductById(productId);

			if (!product.isEmpty()) {
				LOGGER.info("ProductController Rest controller Implementation-getProduct method:End()");
				return new ResponseEntity<>(product, HttpStatus.OK);
			} else {
				LOGGER.error("ProductController Rest controller Implementation- getProduct method EMP list is null:End()");
				return new ResponseEntity<>(product, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			LOGGER.error("Product get all error occured %s", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/searchProduct/{searchKeyword}")
	public ResponseEntity<List<Product>> searchProduct(@PathVariable("searchKeyword") String searchKeyword) {
		try {
			LOGGER.info("ProductController Rest controller Implementation-searchProduct method:Start()");
			List<Product> productList = productService.searchProduct(searchKeyword);

			if (!productList.isEmpty()) {
				LOGGER.info("ProductController Rest controller Implementation-searchProduct method:End()");
				return new ResponseEntity<>(productList, HttpStatus.OK);
			} else {
				LOGGER.error("ProductController Rest controller Implementation- searchProduct method EMP list is null:End()");
				return new ResponseEntity<>(productList, HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			LOGGER.error("Product searchProduct error occured %s", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RolesAllowed("ADMIN")
	@PostMapping("/addProduct")
	public ResponseEntity<Object> addProduct(@RequestBody Product product) {
		try {
			LOGGER.info(" Rest controller Implementation- addProduct() method:Start()");
			Product addedProduct = this.productService.addProduct(product);
			LOGGER.info(" Rest controller Implementation- addProduct() method:End()");
			return ResponseEntity.ok().body(addedProduct);
		} catch (Exception e) {
			LOGGER.info(" Rest controller Implementation- addProduct() Exception: %s ", e);
			return ResponseEntity.ok().body(HttpStatus.NO_CONTENT);
		}
	}

	@DeleteMapping("/deleteProduct/{productId}")
	public Map<String, String> deleteProduct(@PathVariable("productId") Integer productId) {

		try {
			LOGGER.info("Rest controller Implementation- deleteProduct() method:Start()");
			Optional<Product> getProduct = productService.getProductById(productId);

			if (!getProduct.isEmpty()) {
				productService.deleteProduct(productId);
				LOGGER.info(" deleteProduct() method:End()");
				return Collections.singletonMap("success", "Record deleted Successfully");

			} else {
				LOGGER.info("Rest controller Implementation- deleteProduct() method:End()");
				return Collections.singletonMap("Failed", "Record failed to delete. error occurs");
			}
		} catch (Exception e) {
			LOGGER.info("delete request id error occured:%s", e);

			return Collections.singletonMap("Failed", "INTERNAL_SERVER_ERROR");
		}
	}

	@PutMapping("/updateProduct")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
		try {
			LOGGER.info("Rest controller Implementation- updateProduct method:Start()");
			Optional<Product> productOptional = productService.getProductById(product.getId());

			if (!productOptional.isEmpty()) {

				Product getProduct = productOptional.get();
				if (getProduct != null) {
					Product updatedProduct = productService.updateProduct(product);
					LOGGER.info("Rest controller Implementation- updateProduct method:End()");
					return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
				} else {
					LOGGER.info("Rest controller Implementation- updateProduct method - No content is present:End()");
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}
			} else {
				LOGGER.info(" updateProduct error occured");
				LOGGER.info("Rest controller Implementation- updateProduct method - Optional Request is null");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			LOGGER.info("Rest controller Implementation- updateProduct method:%s", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

}
