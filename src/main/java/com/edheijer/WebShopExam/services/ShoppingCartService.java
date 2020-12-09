package com.edheijer.WebShopExam.services;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.edheijer.WebShopExam.models.Cart;
import com.edheijer.WebShopExam.models.Product;


@Service
@SessionScope
public class ShoppingCartService  {
	
	private Cart cart;
	
	public ShoppingCartService() {
		cart  = new Cart();
	}


	public void addProductToCart(Product product) {
		cart.addProductToCart(product);
	}

	
	public void removeProductfromCart(Product product) {
		cart.removeProductfromCart(product);
	}

	
	public Map<Product, Integer> getProductsInCart() {
		return cart.getProductsInCart();
	}

	
	public double getCartSum() {
		return cart.getCartSum();
	}
	
	
	public void incrementQuantity(Product product) {
		cart.incrementQuantity(product);
	}
	
	
	public void decrementQuantity(Product product) {
		cart.decrementQuantity(product);
	}


	

}
