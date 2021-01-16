package com.edheijer.WebShopExam.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edheijer.WebShopExam.dto.OrderLineDTO;
import com.edheijer.WebShopExam.models.OrderLine;
import com.edheijer.WebShopExam.repositories.OrderLineRepository;


@Service
public class OrderLineService {

	@Autowired
	private OrderLineRepository orderLineRepository;
	
	@Autowired
	private OrderLineMapper orderLineMapper;
	
	public OrderLineDTO addOrderLine(OrderLineDTO orderLineDTO) {
		OrderLine orderLine = orderLineMapper.toEntity(orderLineDTO);
		orderLine = orderLineRepository.saveAndFlush(orderLine);
		return orderLineMapper.toDto(orderLine);
	}
}
