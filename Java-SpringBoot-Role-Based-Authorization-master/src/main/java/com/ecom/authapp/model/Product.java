package com.ecom.authapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.web.bind.annotation.CrossOrigin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@CrossOrigin("http://localhost:4200")
@Entity
@Table(name = "product")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 6)
	private int id;

	@Column(name = "user_id", nullable = false, length = 6)
	private int userId;

	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Column(name = "price", nullable = false, length = 100)
	private int price;

	@Column(name = "catagory", nullable = false, length = 50)
	private String catagory;

	@Column(name = "color", nullable = false, length = 50)
	private String color;

	@Column(name = "image",  length = 1024)
	private String image;

	@Column(name = "description", nullable = false, length = 150)
	private String description;

	@Column(name = "quantity", length = 100)
	private int quantity;
}
