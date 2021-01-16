package com.edheijer.WebShopExam.dto;

import com.edheijer.WebShopExam.models.Product;

import lombok.Data;

@Data
public class OrderLineDTO {

	private Long id;
	private int quantity;
	private ProductDTO product;
	private Long orderId;
}
