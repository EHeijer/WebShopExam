package com.edheijer.WebShopExam.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edheijer.WebShopExam.models.Product;
import com.edheijer.WebShopExam.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	public List<Product> getAllSupplements() {
		List<Product> products = productRepository.findAll();
		List<Product> supplements = products
				.stream()
				.filter(p -> p.getCategory().equals(p.getCategory().SUPPLEMENT)).collect(Collectors.toList());
		return supplements;
	}
	
	public List<Product> getAllClothes() {
		List<Product> products = productRepository.findAll();
		List<Product> clothes = products
				.stream()
				.filter(p -> p.getCategory().equals(p.getCategory().CLOTHES)).collect(Collectors.toList());
		return clothes;
	}
	
	public List<Product> getAllShoes() {
		List<Product> products = productRepository.findAll();
		List<Product> shoes = products
				.stream()
				.filter(p -> p.getCategory().equals(p.getCategory().TRAINING_SHOES)).collect(Collectors.toList());
		return shoes;
	}
	
	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}
	
	public Product getById(Long id) {
		return productRepository.getOne(id);
	}

	
	
}
