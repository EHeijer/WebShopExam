package com.edheijer.WebShopExam.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.edheijer.WebShopExam.dto.OrderDTO;
import com.edheijer.WebShopExam.models.Order;
import com.edheijer.WebShopExam.models.Product;
import com.edheijer.WebShopExam.models.Role;
import com.edheijer.WebShopExam.models.RoleEnum;
import com.edheijer.WebShopExam.models.User;
import com.edheijer.WebShopExam.services.OrderMapper;
import com.edheijer.WebShopExam.services.OrderService;
import com.edheijer.WebShopExam.services.ProductService;
import com.edheijer.WebShopExam.services.RoleService;
import com.edheijer.WebShopExam.services.ShoppingCartService;
import com.edheijer.WebShopExam.services.UserService;

@Controller
public class AdminController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private OrderMapper orderMapper;

	@GetMapping("/admin-portal/users")
	public String viewAllUsers(Model model, User user) {
		model.addAttribute("customer", roleService.getRoleByName(RoleEnum.CUSTOMER));
		model.addAttribute("employee", roleService.getRoleByName(RoleEnum.EMPLOYEE));
		model.addAttribute("admin", roleService.getRoleByName(RoleEnum.ADMIN));
		model.addAttribute("roles", roleService.getAllRoles());
		model.addAttribute("role", new Role());
		model.addAttribute("users", userService.getAllUsers());
		model.addAttribute("cartSize", shoppingCartService.getProductsInCart().size());
		return "users";
	}
	
	@GetMapping("/admin-portal/view-products")
	public String getProductRegister(Model model, Product product) {
		model.addAttribute("products", productService.getAllProducts());
		model.addAttribute("images", productService.getAllImages());
		model.addAttribute("cartSize", shoppingCartService.getProductsInCart().size());
		return "view-products";
	}
	
	@GetMapping("/admin-portal/orderhistory")
	public String getOrderHistory(Model model, OrderDTO order) {
		model.addAttribute("orders", orderService.getAllOrders());
		model.addAttribute("cartSize", shoppingCartService.getProductsInCart().size());
		return "orderhistory";
	}
	
	@GetMapping("/orders-to-handle")
	public String getOrdersToHandle(Model model) {
		model.addAttribute("order", new OrderDTO());
		model.addAttribute("orders", orderService.getOrdersToHandle());
		model.addAttribute("cartSize", shoppingCartService.getProductsInCart().size());
		return "orders-to-handle";
	}
	
	@PostMapping("/orders-to-handle/{id}")
	public String handleOrder(@PathVariable("id") Long id, OrderDTO order) {
		OrderDTO realOrder = orderMapper.toDto(orderService.findOrderById(id).get());
		realOrder.setOrderSent(order.isOrderSent());
		orderService.updateOrder(id, realOrder);
		return "redirect:/orders-to-handle";
	}
	
	@PostMapping("/users/disable/{id}")
	public String changeUserStatus(@PathVariable("id") Long id) {
		User userToUpdate = userService.getById(id).get();
		userToUpdate.setEnabled(userToUpdate.isEnabled() == true ? false : true);
		userService.updateUser(id, userToUpdate);
		return "redirect:/admin-portal/users";
	}
	
	@PostMapping("/users/access-level/{id}")
	public String changeAccessLevel(@PathVariable("id") Long id, @ModelAttribute("role") Role role) {
		User userToUpdate = userService.getById(id).get();
		Role getRole = roleService.getRoleByName(role.getName());
		Set<Role> userRoles = userToUpdate.getRoles();
		userRoles.clear();
		userRoles.add(getRole);
		userService.updateUser(id, userToUpdate);
		return "redirect:/admin-portal/users";
	}
}
