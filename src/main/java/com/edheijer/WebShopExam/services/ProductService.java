package com.edheijer.WebShopExam.services;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edheijer.WebShopExam.dto.ProductDTO;
import com.edheijer.WebShopExam.models.Product;
import com.edheijer.WebShopExam.models.ProductCategory;
import com.edheijer.WebShopExam.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired ProductMapper productMapper;
	
	public List<ProductDTO> getAllProducts() {
		return productMapper.toDto(productRepository.findAll());
	}
	
	public List<ProductDTO> getProductsAfterSearching(String searchInput) {
		List<ProductDTO> getProducts = getAllProducts()
				.stream()
				.filter(p -> p.getBrand().toUpperCase().contains(searchInput.toUpperCase()) || p.getProductName().toUpperCase().contains(searchInput.toUpperCase()))
				.collect(Collectors.toList());
		
		return getProducts;		
	}
	
	public List<ProductDTO> getAllProductsNotRemoved() {
		List<ProductDTO> productsNotRemoved = getAllProducts()
				.stream()
				.filter(p -> p.isRemoved() == false)
				.collect(Collectors.toList());
		return productsNotRemoved;
	}
	
	public List<ProductDTO> getAllSupplements() {
		List<ProductDTO> products = getAllProductsNotRemoved();
		
		List<ProductDTO> supplements = products
				.stream()
				.filter(p -> p.getCategory().equals(ProductCategory.SUPPLEMENT)).collect(Collectors.toList());
		return supplements;
	}
	
	public List<ProductDTO> getAllClothes() {
		List<ProductDTO> products = getAllProductsNotRemoved();
		
		List<ProductDTO> clothes = products
				.stream()
				.filter(p -> p.getCategory().equals(ProductCategory.CLOTHES)).collect(Collectors.toList());
		return clothes;
	}
	
	public List<ProductDTO> getAllShoes() {
		List<ProductDTO> products = getAllProductsNotRemoved();
		
		List<ProductDTO> shoes = products
				.stream()
				.filter(p -> p.getCategory().equals(ProductCategory.TRAINING_SHOES)).collect(Collectors.toList());
		return shoes;
	}
	
	public List<String> getAllImages() {
		File file = new File("src/main/resources/static/images");
		File [] files = file.listFiles();
		List<String> imageUrls = new ArrayList<String>();
		for(int i = 0; i < files.length; i++) {
			imageUrls.add(files[i].getName());
		}
		return imageUrls;
	}
	
	public ProductDTO addProduct(ProductDTO productDTO) {
		Product product = productMapper.toEntity(productDTO);
		product = productRepository.saveAndFlush(product);
		return productMapper.toDto(product);
	}
	
	public Optional<ProductDTO> findById(Long id) {
		return productRepository.findById(id)
				.map(productMapper::toDto);
	}
	
	public ProductDTO getById(Long id) {
		return productMapper.toDto(productRepository.getOne(id));
	}

	public void updateProduct(Long id, ProductDTO productDTO) {
		productRepository.saveAndFlush(productMapper.toEntity(productDTO));
	}
	
	public void deleteProductById(Long id) {
		productRepository.deleteById(id);
	}
	
}
