package com.edheijer.WebShopExam.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.edheijer.WebShopExam.models.Order;
import com.edheijer.WebShopExam.models.OrderLine;
import com.edheijer.WebShopExam.models.Product;
import com.edheijer.WebShopExam.services.OrderLineService;
import com.edheijer.WebShopExam.services.OrderService;
import com.edheijer.WebShopExam.services.ShoppingCartService;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderLineService orderLineService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;

	@PostMapping(path = "/orderconfirm")
	public ModelAndView addOrder() {
		if(shoppingCartService.getProductsInCart().isEmpty()) {
			ModelAndView modelAndView = new ModelAndView("redirect:/shoppingcart");
			modelAndView.addObject("cart", shoppingCartService.getProductsInCart());
			return modelAndView;
		} else {
		Order order = new Order();
		orderService.addOrder(order);
		List<OrderLine> orderLines = new ArrayList<OrderLine>();
		Map<Product, Integer> cart = shoppingCartService.getProductsInCart();
		System.out.println(cart);
		for(Map.Entry<Product, Integer> entry : cart.entrySet()) {
			OrderLine orderLine = new OrderLine();
			orderLine.setOrder(order);
			orderLine.setProduct(entry.getKey());
			orderLine.setQuantity(entry.getValue());
			addOrderLine(orderLine);
			orderLines.add(orderLine);
		}
		order.setOrderLines(orderLines);
		ModelAndView modelAndView = new ModelAndView("/orderconfirm");
		modelAndView.addObject("order", order);
		modelAndView.addObject("orderSum", order.getTotalOrderPrice());
		shoppingCartService.getProductsInCart().clear();
		return modelAndView;
		}
	}
	
	@PostMapping(path = "/orderLines")
	public void addOrderLine(OrderLine orderLine) {
		orderLineService.addOrderLine(orderLine);
	}
	
	@GetMapping(path = "/orders") 
	public List<Order> getAllOrderLines(){
		return orderService.getAllOrders();
	}
}
