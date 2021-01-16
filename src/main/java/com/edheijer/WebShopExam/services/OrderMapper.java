package com.edheijer.WebShopExam.services;

import com.edheijer.WebShopExam.dto.OrderDTO;
import com.edheijer.WebShopExam.models.Order;

import java.time.LocalDate;

import org.mapstruct.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring", uses = {UserMapper.class, OrderLineMapper.class}, imports = {LocalDate.class})
public interface OrderMapper extends EntityMapper<OrderDTO, Order>{

	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "order.orderLines", target = "orderLineDTOList")
	@Mapping(target = "dateCreated", expression = "java(LocalDate.now())")
	@Mapping(source = "order.orderSent", target = "orderSent", defaultValue = "false")
	OrderDTO toDto(Order order);
	
	@Mapping(source = "userId", target = "user")
	@Mapping(source = "orderLineDTOList", target = "orderLines")
	@Mapping(target = "dateCreated", expression = "java(LocalDate.now())")
	@Mapping(source = "orderSent", target = "orderSent", defaultValue = "false")
	Order toEntity(OrderDTO orderDTO);
}
