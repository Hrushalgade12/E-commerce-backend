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
@Table(name = "ordert")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 6)
	private int id;

	@Column(name = "user_id", nullable = false, length = 6)
	private int userId;

	@Column(name = "email", nullable = false, length = 50)
	private String email;

	@Column(name = "total_price", nullable = false, length = 100)
	private int totalPrice;

	@Column(name = "address", nullable = false, length = 50)
	private String address;

	@Column(name = "contact", nullable = false, length = 50)
	private String contact;

}
