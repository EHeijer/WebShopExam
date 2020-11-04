package com.edheijer.WebShopExam.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.edheijer.WebShopExam.models.Product;
import com.edheijer.WebShopExam.repositories.ProductRepository;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
	
	@Autowired
	private ProductRepository productRepository;
	
	private Map<Product, Integer> products = new HashMap<Product, Integer>();

	@Override
	public void addProductToCart(Product product) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeProductfromCart(Product product) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<Product, Integer> getProductsInCart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkoutCart() {
		// TODO Auto-generated method stub

	}

	@Override
	public double getCartSum() {
		// TODO Auto-generated method stub
		return 0;
	}

}
