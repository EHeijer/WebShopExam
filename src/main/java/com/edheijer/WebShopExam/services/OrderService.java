package com.edheijer.WebShopExam.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edheijer.WebShopExam.models.Order;
import com.edheijer.WebShopExam.repositories.OrderRepository;



@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Transactional
	public Order addOrder(Order Order) {
		return orderRepository.saveAndFlush(Order);
	}
	
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}
	
	public Optional<Order> findOrderById(Long id) {
		return orderRepository.findById(id);
	}
	
	public void updateOrder(Long id, Order order) {
		orderRepository.saveAndFlush(order);
	}
	
	public List<Order> getOrdersToHandle() {
		List<Order> ordersToHandle = getAllOrders()
				.stream()
				.filter(o -> o.isOrderSent() == false)
				.collect(Collectors.toList());
		
		return ordersToHandle;
	}
}
