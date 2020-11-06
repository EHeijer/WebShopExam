package com.edheijer.WebShopExam.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.edheijer.WebShopExam.models.Product;
import com.edheijer.WebShopExam.repositories.OrderRepository;
import com.edheijer.WebShopExam.repositories.ProductRepository;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	private Map<Product, Integer> cart = new HashMap<Product, Integer>();

	@Override
	public void addProductToCart(Product product) {
		if(cart.containsKey(product)) {
			cart.replace(product, cart.get(product) + 1);
		} else {
			cart.put(product, 1);
		}

	}

	@Override
	public void removeProductfromCart(Product product) {
		if(cart.containsKey(product)) {
			if(cart.get(product) > 1) {
				cart.replace(product, cart.get(product) - 1);
			} else if(cart.get(product) == 1) {
				cart.remove(product);
			}
		}

	}

	@Override
	public Map<Product, Integer> getProductsInCart() {
		return cart;
	}

	@Override
	public void checkoutCart() {
		

	}

	@Override
	public double getCartSum() {
		// TODO Auto-generated method stub
		return 0;
	}

}
