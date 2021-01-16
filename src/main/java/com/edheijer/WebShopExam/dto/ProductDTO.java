package com.edheijer.WebShopExam.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.edheijer.WebShopExam.models.ProductCategory;

import lombok.Data;

@Data
public class ProductDTO {

	private Long id;
	private String brand;
	private String productName;
	private double price;
	private String imageUrl;
	private boolean removed;
	private String shelfNumber;
	private ProductCategory category;
}
