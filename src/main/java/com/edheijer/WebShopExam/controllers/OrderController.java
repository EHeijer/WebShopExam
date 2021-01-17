package com.edheijer.WebShopExam.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edheijer.WebShopExam.dto.OrderDTO;
import com.edheijer.WebShopExam.dto.OrderLineDTO;
import com.edheijer.WebShopExam.dto.ProductDTO;
import com.edheijer.WebShopExam.dto.UserDTO;
import com.edheijer.WebShopExam.models.Order;
import com.edheijer.WebShopExam.models.OrderLine;
import com.edheijer.WebShopExam.models.Product;
import com.edheijer.WebShopExam.models.User;
import com.edheijer.WebShopExam.security.UserDetailsImpl;
import com.edheijer.WebShopExam.services.OrderLineService;
import com.edheijer.WebShopExam.services.OrderService;
import com.edheijer.WebShopExam.services.ShoppingCartService;
import com.edheijer.WebShopExam.services.UserService;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderLineService orderLineService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private UserService userService;

	@PostMapping(path = "/orderconfirm")
	public ModelAndView addOrder(@AuthenticationPrincipal UserDetailsImpl authUser) {
		if(shoppingCartService.getProductsInCart().isEmpty()) {
			ModelAndView modelAndView = new ModelAndView("redirect:/shoppingcart");
			modelAndView.addObject("cart", shoppingCartService.getProductsInCart());
			return modelAndView;
		} else {
		OrderDTO orderDTO = new OrderDTO();
		UserDTO currentUser = userService.getUserAndFetchOrders(authUser.getUserId());
		orderDTO.setUserId(currentUser.getId());
		orderDTO = orderService.addOrder(orderDTO);
		List<OrderLineDTO> orderLinesDTOs = handleOrderlines(orderDTO);
		orderDTO.setOrderLineDTOList(orderLinesDTOs);
		orderDTO = orderService.updateOrder(orderDTO.getId(), orderDTO);
		ModelAndView modelAndView = prepareOrderConfirm(orderDTO);
		System.out.println(orderDTO);
		return modelAndView;
		}
	}

	private ModelAndView prepareOrderConfirm(OrderDTO orderDTO) {
		ModelAndView modelAndView = new ModelAndView("/orderconfirm");
		modelAndView.addObject("order", orderDTO);
		shoppingCartService.clearCart();
		modelAndView.addObject("cartSize", shoppingCartService.getProductsInCart().size());
		return modelAndView;
	}

	private List<OrderLineDTO> handleOrderlines(OrderDTO orderDTO) {
		List<OrderLineDTO> orderLinesDTOs = new ArrayList<OrderLineDTO>();
		Map<ProductDTO, Integer> cart = shoppingCartService.getProductsInCart();
		Double sum = 0D;
		for(Map.Entry<ProductDTO, Integer> entry : cart.entrySet()) {
			OrderLineDTO orderLineDTO = new OrderLineDTO();
			orderLineDTO.setQuantity(entry.getValue());
			orderLineDTO.setOrderId(orderDTO.getId());
			orderLineDTO.setProduct(entry.getKey());
			orderLineDTO = addOrderLine(orderLineDTO);
			orderLinesDTOs.add(orderLineDTO);
			sum += (entry.getValue() * entry.getKey().getPrice());
		}
		orderDTO.setOrderSum(sum);
		return orderLinesDTOs;
	}
	
	@PostMapping(path = "/orderLines")
	public OrderLineDTO addOrderLine(OrderLineDTO orderLineDTO) {
		return orderLineService.addOrderLine(orderLineDTO);
	}
	
	@GetMapping(path = "/orders") 
	public List<OrderDTO> getAllOrderLines(){
		return orderService.getAllOrders();
	}
}
