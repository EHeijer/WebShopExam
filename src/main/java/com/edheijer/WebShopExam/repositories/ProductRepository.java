package com.edheijer.WebShopExam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edheijer.WebShopExam.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
