package com.edheijer.WebShopExam.services;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.edheijer.WebShopExam.dto.OrderLineDTO;
import com.edheijer.WebShopExam.models.OrderLine;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, OrderMapper.class})
public interface OrderLineMapper extends EntityMapper<OrderLineDTO, OrderLine>{
	
//	@Mapping(source = "product.id", target = "productId")
	@Mapping(source = "order.id", target = "orderId")
	OrderLineDTO toDto(OrderLine orderLine);
	
//	@Mapping(source = "productId", target = "product.id")
	@Mapping(source = "orderId", target = "order.id")
	OrderLine toEntity(OrderLineDTO orderLineDTO);
	
}
