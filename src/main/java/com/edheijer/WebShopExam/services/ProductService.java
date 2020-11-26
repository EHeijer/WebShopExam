package com.edheijer.WebShopExam.services;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edheijer.WebShopExam.models.Product;
import com.edheijer.WebShopExam.models.ProductCategory;
import com.edheijer.WebShopExam.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	public List<Product> getProductsAfterSearching(String searchInput) {
		List<Product> getProducts = getAllProducts()
				.stream()
				.filter(p -> p.getBrand().toUpperCase().contains(searchInput.toUpperCase()) || p.getProductName().toUpperCase().contains(searchInput.toUpperCase()))
				.collect(Collectors.toList());
		
		return getProducts;		
	}
	
	public List<Product> getAllProductsNotRemoved() {
		List<Product> productsNotRemoved = getAllProducts()
				.stream()
				.filter(p -> p.isRemoved() == false)
				.collect(Collectors.toList());
		return productsNotRemoved;
	}
	
	public List<Product> getAllSupplements() {
		List<Product> products = getAllProductsNotRemoved();
		
		List<Product> supplements = products
				.stream()
				.filter(p -> p.getCategory().equals(ProductCategory.SUPPLEMENT)).collect(Collectors.toList());
		return supplements;
	}
	
	public List<Product> getAllClothes() {
		List<Product> products = getAllProductsNotRemoved();
		
		List<Product> clothes = products
				.stream()
				.filter(p -> p.getCategory().equals(ProductCategory.CLOTHES)).collect(Collectors.toList());
		return clothes;
	}
	
	public List<Product> getAllShoes() {
		List<Product> products = getAllProductsNotRemoved();
		
		List<Product> shoes = products
				.stream()
				.filter(p -> p.getCategory().equals(ProductCategory.TRAINING_SHOES)).collect(Collectors.toList());
		return shoes;
	}
	
	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}
	
	public Product getById(Long id) {
		return productRepository.getOne(id);
	}

	public void updateProduct(Long id, Product product) {
		productRepository.saveAndFlush(product);
	}
	
	public void deleteProductById(Long id) {
		productRepository.deleteById(id);
	}
	
}
