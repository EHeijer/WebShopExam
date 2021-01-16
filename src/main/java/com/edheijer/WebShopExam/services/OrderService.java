package com.edheijer.WebShopExam.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edheijer.WebShopExam.dto.OrderDTO;
import com.edheijer.WebShopExam.models.Order;
import com.edheijer.WebShopExam.repositories.OrderRepository;
import com.edheijer.WebShopExam.services.OrderMapper;



@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderMapper orderMapper;
	
	
	
	public OrderDTO addOrder(OrderDTO orderDTO) {
		Order order = orderMapper.toEntity(orderDTO);
		order = orderRepository.saveAndFlush(order);
		return orderMapper.toDto(order);
	}
	
	public List<OrderDTO> getAllOrders() {
		return orderMapper.toDto(orderRepository.findAll());
	}
	
	public Optional<Order> findOrderById(Long id) {
		return orderRepository.findById(id);
	}
	
	public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
		Order order = orderRepository.saveAndFlush(orderMapper.toEntity(orderDTO));
		return orderMapper.toDto(order);
	}
	
	public List<OrderDTO> getOrdersToHandle() {
		List<OrderDTO> ordersToHandle = getAllOrders()
				.stream()
				.filter(o -> o.isOrderSent() == false)
				.collect(Collectors.toList());
		
		return ordersToHandle;
	}
}
