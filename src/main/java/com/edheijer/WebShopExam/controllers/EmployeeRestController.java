package com.edheijer.WebShopExam.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edheijer.WebShopExam.models.Order;
import com.edheijer.WebShopExam.models.Product;
import com.edheijer.WebShopExam.models.User;
import com.edheijer.WebShopExam.services.OrderService;
import com.edheijer.WebShopExam.services.ProductService;
import com.edheijer.WebShopExam.services.UserService;

@RestController
@RequestMapping("/employee-actions/")
@CrossOrigin
public class EmployeeRestController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	
	@GetMapping("/orders")
	public List<Order> getOrderToHandle(){
		return orderService.getAllOrders();
	}
	
	
	@PutMapping(path = "/handle-orders/{id}")
	public void updateOrder(@PathVariable("id") int id, @RequestBody Order order) {
		orderService.updateOrder(Integer.toUnsignedLong(id), order);
	}
	
	@GetMapping("/products")
	public List<Product> getProducts(){
		return productService.getAllProducts();
	}
	
	@PutMapping(path = "/products/{id}")
	public void updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
		productService.updateProduct(Integer.toUnsignedLong(id), product);
	}
	
	@PostMapping(path = "/products")
	public void addProduct(@RequestBody Product product) {
		productService.addProduct(product);
	}
}
