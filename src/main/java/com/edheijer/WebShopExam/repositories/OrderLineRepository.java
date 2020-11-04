package com.edheijer.WebShopExam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edheijer.WebShopExam.models.OrderLine;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long>{

}
