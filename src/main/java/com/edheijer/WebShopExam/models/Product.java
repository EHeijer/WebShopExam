package com.edheijer.WebShopExam.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String brand;
	private String productName;
	private double price;
	private String imageUrl;
	private boolean removed = false;
	private String shelfNumber;
	
	@Enumerated(EnumType.STRING)
	private ProductCategory category;

	public Product(Long id, String brand, String productName, double price, String imageUrl, String shelfNumber,
			ProductCategory category) {
		this.id = id;
		this.brand = brand;
		this.productName = productName;
		this.price = price;
		this.imageUrl = imageUrl;
		this.shelfNumber = shelfNumber;
		this.category = category;
	}
	
	
}
