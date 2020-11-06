package com.edheijer.WebShopExam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edheijer.WebShopExam.services.ProductService;
import com.edheijer.WebShopExam.services.ShoppingCartService;

@Controller
@RequestMapping
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@GetMapping("/products")
	public String getAllProducts(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products";
	}
	
	@GetMapping("/shoppingcart")
	public String shoppingCart(Model model) {
//		ModelAndView modelAndView = new ModelAndView("/shoppingcart");
		model.addAttribute("cart", shoppingCartService.getProductsInCart());
		return "shoppingcart";
	}
	
	@GetMapping("/products/{productId}")
	public String addProductToCart(@PathVariable("productId") Long productId) {
		productService.findById(productId).ifPresent(shoppingCartService::addProductToCart);
		System.out.println(shoppingCartService.getProductsInCart());
		return "redirect:/products";
	}
	
}
