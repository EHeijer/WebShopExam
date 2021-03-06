package com.edheijer.WebShopExam.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edheijer.WebShopExam.dto.ProductDTO;
import com.edheijer.WebShopExam.models.Product;
import com.edheijer.WebShopExam.services.ProductMapper;
import com.edheijer.WebShopExam.services.ProductService;
import com.edheijer.WebShopExam.services.ShoppingCartService;

@Controller
@RequestMapping
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private ProductMapper productMapper;
	
	@GetMapping("")
	public String viewHomePage() {
		return "redirect:/products";
	}
	
	@GetMapping("/products")
	public String getAllProducts(Model model) {
		model.addAttribute("product", new ProductDTO());
		model.addAttribute("products", productService.getAllProductsNotRemoved());
		model.addAttribute("cartSize", shoppingCartService.getProductsInCart().size());
		return "products";
	}
	
	@GetMapping("/supplements")
	public String getAllSupplements(Model model) {
		model.addAttribute("product", new ProductDTO());
		model.addAttribute("supplements", productService.getAllSupplements());
		model.addAttribute("cartSize", shoppingCartService.getProductsInCart().size());
		return "supplements";
	}
	
	@GetMapping("/clothes")
	public String getAllClothes(Model model) {
		model.addAttribute("product", new ProductDTO());
		model.addAttribute("clothes", productService.getAllClothes());
		model.addAttribute("cartSize", shoppingCartService.getProductsInCart().size());
		return "clothes";
	}
	
	@GetMapping("/shoes")
	public String getAllShoes(Model model) {
		model.addAttribute("product", new ProductDTO());
		model.addAttribute("shoes", productService.getAllShoes());
		model.addAttribute("cartSize", shoppingCartService.getProductsInCart().size());
		return "shoes";
	}
	
	
	@GetMapping("/products/{productId}")
	public String addProductToCart(@PathVariable("productId") Long productId) {
		Optional<ProductDTO> optionalProduct = productService.findById(productId);
		if(optionalProduct.isPresent()){
			shoppingCartService.addProductToCart(optionalProduct.get());
			
		}
		return "redirect:/products";
		
	}
	
	@PostMapping("/products/edit/{productId}")
	public String editProduct(@PathVariable("productId") Long productId,@ModelAttribute("product") ProductDTO product) {
		editAndUpdate(productId, product);
		return "redirect:/products";
	}

	@PostMapping("/admin-portal/products/edit/{productId}")
	public String editProductView(@PathVariable("productId") Long productId,@ModelAttribute("product") ProductDTO product) {
		editAndUpdate(productId, product);
		return "redirect:/admin-portal/view-products";
	}
	
	private void editAndUpdate(Long productId, ProductDTO product) {
		Optional<ProductDTO> optionalProduct = productService.findById(productId);
		if(optionalProduct.isPresent()) {
			ProductDTO productToUpdate = optionalProduct.get();
			productToUpdate.setProductName(!product.getProductName().isEmpty() ? product.getProductName() : productToUpdate.getProductName());
			productToUpdate.setBrand(!product.getBrand().isEmpty() ? product.getBrand() : productToUpdate.getBrand());
			productToUpdate.setPrice(product.getPrice() != 0D ? product.getPrice() : productToUpdate.getPrice());
			productService.updateProduct(productId, productToUpdate);
		}
	}
	
	@PostMapping("/admin-portal/add-product")
	public String addProduct(@ModelAttribute("product") ProductDTO product) {
		productService.addProduct(product);
		return "redirect:/admin-portal/view-products";
	}
	
	@GetMapping("/products/delete/{productId}")
	public String deleteProductFromView(@PathVariable("productId") Long productId) {
		setRemovedAndUpdate(productId);
		return "redirect:/products";
	}

	@GetMapping("/admin-portal/products/delete/{productId}")
	public String deleteProductFromAdminView(@PathVariable("productId") Long productId) {
		setRemovedAndUpdate(productId);
		return "redirect:/admin-portal/view-products";
	}
	
	private void setRemovedAndUpdate(Long productId) {
		Optional<ProductDTO> optionalProduct = productService.findById(productId);
		if(optionalProduct.isPresent()) {
			ProductDTO productToUpdate = optionalProduct.get();
			productToUpdate.setRemoved(true);
			productService.updateProduct(productId, productToUpdate);
		}
	}
	@GetMapping("/admin-portal/products/addToView/{productId}")
	public String addProductFromAdminView(@PathVariable("productId") Long productId) {
		Optional<ProductDTO> optionalProduct = productService.findById(productId);
		if(optionalProduct.isPresent()) {
			ProductDTO productToUpdate = optionalProduct.get();
			productToUpdate.setRemoved(false);
			productService.updateProduct(productId, productToUpdate);
			return "redirect:/admin-portal/view-products";
		}
		return "error-500";
	}
	
	@GetMapping("/search")
	public String searchProducts(@RequestParam(value = "searchInput") String searchInput, Model model) {
		model.addAttribute("product", new ProductDTO());
		List<ProductDTO> products = productService.getProductsAfterSearching(searchInput);
		if(products.isEmpty()) {
			return "redirect:/products";
		}else {
			model.addAttribute("products", products);
			model.addAttribute("cartSize", shoppingCartService.getProductsInCart().size());
			return "products-after-search";
		}
		
	}
	
	
}
