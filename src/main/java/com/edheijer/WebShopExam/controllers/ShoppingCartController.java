package com.edheijer.WebShopExam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.edheijer.WebShopExam.models.Product;
import com.edheijer.WebShopExam.services.ProductService;
import com.edheijer.WebShopExam.services.ShoppingCartServiceImpl;

@Controller
public class ShoppingCartController {
	
	@Autowired
	private ShoppingCartServiceImpl shoppingCartService;
	
	@Autowired
	private ProductService productService;

	@GetMapping("/shoppingcart")
	public String shoppingCart(Model model) {
		model.addAttribute("cart", shoppingCartService.getProductsInCart());
		model.addAttribute("cartSum", shoppingCartService.getCartSum());
		model.addAttribute("cartSize", shoppingCartService.getProductsInCart().size());
		return "shoppingcart";
	}
	
	@GetMapping("/shoppingcart/product/{productId}")
	public String incrementQuantity(@PathVariable("productId") Long productId, Model model) {
		Product product = productService.getById(productId);
		shoppingCartService.incrementQuantity(product);
		model.addAttribute("cart", shoppingCartService.getProductsInCart());
		return "redirect:/shoppingcart";
	}
	
	@GetMapping("/shoppingcart/products/{productId}")
	public String decrementQuantity(@PathVariable("productId") Long productId) {
		Product product = productService.getById(productId);
		if(shoppingCartService.getProductsInCart().get(product) <= 1) {
			shoppingCartService.removeProductfromCart(product);
		}else {
			shoppingCartService.decrementQuantity(product);
		}
		return "redirect:/shoppingcart";
	}
	
	@GetMapping("/shoppingcart/products/delete/{productId}")
	public String removeFromCart(@PathVariable("productId") Long productId) {
		Product product = productService.getById(productId);
		shoppingCartService.removeProductfromCart(product);
		return "redirect:/shoppingcart";
	}
	
}
