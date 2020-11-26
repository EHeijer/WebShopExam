package com.edheijer.WebShopExam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edheijer.WebShopExam.models.Product;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>{
	Optional<Product> findById(Long id);
	
	Optional<Product> findByBrand(String brand);
}
