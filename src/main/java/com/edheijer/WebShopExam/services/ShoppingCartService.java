package com.edheijer.WebShopExam.services;

import java.util.Map;

import com.edheijer.WebShopExam.models.Product;

public interface ShoppingCartService {
	void addProductToCart(Product product);
	
	void removeProductfromCart(Product product);
	
	Map<Product, Integer> getProductsInCart();
	
	double getCartSum();
	
	void incrementQuantity(Product product);
	
	void decrementQuantity(Product product);
}
