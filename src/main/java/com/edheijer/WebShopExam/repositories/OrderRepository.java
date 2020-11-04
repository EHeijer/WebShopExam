package com.edheijer.WebShopExam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edheijer.WebShopExam.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
