package com.edheijer.WebShopExam.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edheijer.WebShopExam.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	
}
