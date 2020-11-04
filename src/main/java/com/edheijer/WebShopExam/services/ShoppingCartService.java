package com.edheijer.WebShopExam.services;

import java.util.List;
import java.util.Map;

import com.edheijer.WebShopExam.models.Product;

public interface ShoppingCartService {
	void addProductToCart(Product product);
	
	void removeProductfromCart(Product product);
	
	Map<Product, Integer> getProductsInCart();
	
	void checkoutCart();
	
	double getCartSum();
}
