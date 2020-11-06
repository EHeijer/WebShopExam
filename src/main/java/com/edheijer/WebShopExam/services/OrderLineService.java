package com.edheijer.WebShopExam.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edheijer.WebShopExam.models.OrderLine;
import com.edheijer.WebShopExam.repositories.OrderLineRepository;


@Service
public class OrderLineService {

	@Autowired
	private OrderLineRepository orderLineRepository;
	
	public OrderLine addOrderLine(OrderLine orderLine) {
		return orderLineRepository.saveAndFlush(orderLine);
	}
}
