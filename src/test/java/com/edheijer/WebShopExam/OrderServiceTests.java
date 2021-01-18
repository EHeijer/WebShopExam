package com.edheijer.WebShopExam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.edheijer.WebShopExam.dto.OrderDTO;
import com.edheijer.WebShopExam.models.Order;
import com.edheijer.WebShopExam.repositories.OrderRepository;
import com.edheijer.WebShopExam.services.OrderMapper;
import com.edheijer.WebShopExam.services.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTests {

	@Autowired
	private OrderService orderService;
	
	@MockBean
	private OrderRepository orderRepository;
	
	@Autowired 
	OrderMapper orderMapper;
	
	@Test
	public void getAllOrdersTest() {
		when(orderRepository.findAll()).thenReturn(Stream
				.of(new Order(),new Order()).collect(Collectors.toList()));
		
		assertEquals(2, orderService.getAllOrders().size());
	}
	
	@Test
	public void addOrderTest() {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(1L);
		Order order = orderMapper.toEntity(orderDTO);
		when(orderRepository.saveAndFlush(order)).thenReturn(order);
		
		assertEquals(orderDTO.getId(), orderService.addOrder(orderDTO).getId());
		verify(orderRepository, times(1)).saveAndFlush(orderMapper.toEntity(orderDTO));
	}
	
	@Test
	public void findOrderByIdTest() {
		Order order = new Order();
		order.setId(1L);
		when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
		assertEquals(Optional.of(order), orderService.findOrderById(1L));
	}
	
	
}
