package com.edheijer.WebShopExam.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edheijer.WebShopExam.models.Order;
import com.edheijer.WebShopExam.repositories.OrderRepository;



@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	public Order addOrder(Order Order) {
		return orderRepository.saveAndFlush(Order);
	}
	
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}
}
