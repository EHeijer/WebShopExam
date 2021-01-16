package com.edheijer.WebShopExam.models;

import java.util.HashMap;
import java.util.Map;

public class Cart {

	private Map<Product, Integer> cart;
	
	public Cart() {
		cart  = new HashMap<>();
	}


	public void addProductToCart(Product product) {
		if(cart.containsKey(product)) {
			cart.replace(product, cart.get(product) + 1);
		} else {
			cart.put(product, 1);
		}

	}

	
	public void removeProductfromCart(Product product) {
		if(cart.containsKey(product)) {
			cart.remove(product);
		}

	}

	
	public Map<Product, Integer> getProductsInCart() {
		return cart;
	}

	
	public double getCartSum() {
		double cartSum = 0;
		for(Map.Entry<Product, Integer> entry : cart.entrySet()) {
			double sumOfOrderLine = entry.getKey().getPrice() * entry.getValue();
			cartSum = cartSum += sumOfOrderLine;
		}
		return cartSum;
	}
	
	
	public void incrementQuantity(Product product) {
		cart.replace(product, cart.get(product) + 1);
	}
	
	
	public void decrementQuantity(Product product) {
		if(getProductsInCart().get(product) <= 1) {
			removeProductfromCart(product);
		}else {
			cart.replace(product, cart.get(product) - 1);
		}
		
	}
}
