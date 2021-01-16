package com.edheijer.WebShopExam.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.edheijer.WebShopExam.dto.ProductDTO;
import com.edheijer.WebShopExam.models.Cart;
import com.edheijer.WebShopExam.models.Product;


@Service
@SessionScope
public class ShoppingCartService  {
	
	private Cart cart;
	
	@Autowired
	private ProductMapper productMapper;
	
	public ShoppingCartService() {
		cart  = new Cart();
	}


	public void addProductToCart(ProductDTO productDTO) {
		Product product = productMapper.toEntity(productDTO);
		cart.addProductToCart(product);
	}

	
	public void removeProductfromCart(ProductDTO productDTO) {
		cart.removeProductfromCart(productMapper.toEntity(productDTO));
	}

	
	public Map<ProductDTO, Integer> getProductsInCart() {
		Map<ProductDTO, Integer> products = new HashMap<ProductDTO, Integer>();
		for(Map.Entry<Product, Integer> entry : cart.getProductsInCart().entrySet()) {
			products.put(productMapper.toDto(entry.getKey()), entry.getValue());
		};
		return products;
	}

	
	public double getCartSum() {
		return cart.getCartSum();
	}
	
	
	public void incrementQuantity(ProductDTO productDTO) {
		cart.incrementQuantity(productMapper.toEntity(productDTO));
	}
	
	
	public void decrementQuantity(ProductDTO productDTO) {
		cart.decrementQuantity(productMapper.toEntity(productDTO));
	}


	public void clearCart() {
		cart.getProductsInCart().clear();
		getProductsInCart().clear();
	}


	

}
