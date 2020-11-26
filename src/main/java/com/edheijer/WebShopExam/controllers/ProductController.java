package com.edheijer.WebShopExam.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.edheijer.WebShopExam.models.Product;
import com.edheijer.WebShopExam.services.ProductService;
import com.edheijer.WebShopExam.services.ShoppingCartService;
import com.edheijer.WebShopExam.services.ShoppingCartServiceImpl;

@Controller
@RequestMapping
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ShoppingCartServiceImpl shoppingCartService;
	
	@GetMapping("/products")
	public String getAllProducts(Model model, Product product) {
		model.addAttribute("products", productService.getAllProductsNotRemoved());
		model.addAttribute("cartSize", shoppingCartService.getProductsInCart().size());
		return "products";
	}
	
	@GetMapping("/supplements")
	public String getAllSupplements(Model model) {
		model.addAttribute("supplements", productService.getAllSupplements());
		model.addAttribute("cartSize", shoppingCartService.getProductsInCart().size());
		return "supplements";
	}
	
	@GetMapping("/clothes")
	public String getAllClothes(Model model) {
		model.addAttribute("clothes", productService.getAllClothes());
		model.addAttribute("cartSize", shoppingCartService.getProductsInCart().size());
		return "clothes";
	}
	
	@GetMapping("/shoes")
	public String getAllShoes(Model model) {
		model.addAttribute("shoes", productService.getAllShoes());
		model.addAttribute("cartSize", shoppingCartService.getProductsInCart().size());
		return "shoes";
	}
	
	@GetMapping("/shoppingcart")
	public String shoppingCart(Model model) {
//		ModelAndView modelAndView = new ModelAndView("/shoppingcart");
		model.addAttribute("cart", shoppingCartService.getProductsInCart());
		model.addAttribute("cartSum", shoppingCartService.getCartSum());
		model.addAttribute("cartSize", shoppingCartService.getProductsInCart().size());
		return "shoppingcart";
	}
	
	@GetMapping("/shoppingcart/product/{productId}")
	public String incrementQuantity(@PathVariable("productId") Long productId, Model model) {
		Product product = productService.getById(productId);
		System.out.println(product);
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
	
	@GetMapping("/products/{productId}")
	public String addProductToCart(@PathVariable("productId") Long productId) {
		productService.findById(productId).ifPresent(shoppingCartService::addProductToCart);
		System.out.println(shoppingCartService.getProductsInCart().size());
		return "redirect:/products";
	}
	
	@PostMapping("/products/edit/{productId}")
	public String editProduct(@PathVariable("productId") Long productId,@ModelAttribute("product") Product product) {
		Product productToUpdate = productService.findById(productId).get();
		productToUpdate.setProductName(!product.getProductName().isEmpty() ? product.getProductName() : productToUpdate.getProductName());
		productToUpdate.setBrand(!product.getBrand().isEmpty() ? product.getBrand() : productToUpdate.getBrand());
		productToUpdate.setPrice(product.getPrice() != 0D ? product.getPrice() : productToUpdate.getPrice());
		productService.updateProduct(productId, productToUpdate);
		return "redirect:/products";
	}
	
	@PostMapping("/admin-portal/products/edit/{productId}")
	public String editProductView(@PathVariable("productId") Long productId,@ModelAttribute("product") Product product) {
		Product productToUpdate = productService.findById(productId).get();
		productToUpdate.setProductName(!product.getProductName().isEmpty() ? product.getProductName() : productToUpdate.getProductName());
		productToUpdate.setBrand(!product.getBrand().isEmpty() ? product.getBrand() : productToUpdate.getBrand());
		productToUpdate.setPrice(product.getPrice() != 0D ? product.getPrice() : productToUpdate.getPrice());
		productService.updateProduct(productId, productToUpdate);
		return "redirect:/admin-portal/view-products";
	}
	
	@GetMapping("/products/delete/{productId}")
	public String deleteProductFromView(@PathVariable("productId") Long productId) {
		Product product = productService.findById(productId).get();
		product.setRemoved(true);
		productService.updateProduct(productId, product);
		return "redirect:/products";
	}
	@GetMapping("/admin-portal/products/delete/{productId}")
	public String deleteProductFromAdminView(@PathVariable("productId") Long productId) {
		Product product = productService.findById(productId).get();
		product.setRemoved(true);
		productService.updateProduct(productId, product);
		return "redirect:/admin-portal/view-products";
	}
	@GetMapping("/admin-portal/products/addToView/{productId}")
	public String addProductFromAdminView(@PathVariable("productId") Long productId) {
		Product product = productService.findById(productId).get();
		product.setRemoved(false);
		productService.updateProduct(productId, product);
		return "redirect:/admin-portal/view-products";
	}
	
	@GetMapping("/search")
	public String searchProducts(@RequestParam(value = "searchInput") String searchInput, Model model, Product product) {
		System.out.println(searchInput);
		List<Product> products = productService.getProductsAfterSearching(searchInput);
		System.out.println(products);
		if(products.isEmpty()) {
			return "redirect:/products";
		}else {
			model.addAttribute("products", products);
			return "products-after-search";
		}
		
	}
	
	
}
