package com.edheijer.WebShopExam.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edheijer.WebShopExam.models.Order;
import com.edheijer.WebShopExam.models.User;
import com.edheijer.WebShopExam.security.UserDetailsImpl;
import com.edheijer.WebShopExam.services.OrderService;
import com.edheijer.WebShopExam.services.UserService;
import com.zaxxer.hikari.util.SuspendResumeLock;

@Controller
public class UserController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@GetMapping("/customer-profile")
	public String customerProfile(@AuthenticationPrincipal UserDetailsImpl user, Model model) {
		model.addAttribute("user", user);
		model.addAttribute("orders", user.getUserOrders());
		return "customer-profile";
	}
	
	@GetMapping("/employee-profile")
	public String employeeProfile(@AuthenticationPrincipal UserDetailsImpl user, Model model) {
		model.addAttribute("user", user);
		return "employee-profile";
	}
	
	@GetMapping("/admin-portal")
	public String adminPortal(@AuthenticationPrincipal UserDetailsImpl user, Model model) {
		model.addAttribute("user", user);
		return "admin-portal";
	}
	
	@GetMapping("/admin-portal/admin-profile")
	public String adminProfile(@AuthenticationPrincipal UserDetailsImpl user, Model model) {
		model.addAttribute("user", user);
		return "admin-profile";
	}
	
	@PostMapping("/customer-profile/edit")
	public String updateUserInfo(@AuthenticationPrincipal UserDetailsImpl authUser,@ModelAttribute("user") User user, Model model) {
		User userToUpdate = userService.getByUserId(authUser.getUserId());
		userToUpdate.setEmail(user.getEmail() != null ? user.getEmail() : userToUpdate.getEmail());
		userToUpdate.setUsername(user.getUsername() != null ? user.getUsername() : userToUpdate.getUsername());
		userToUpdate.setUserOrders(userToUpdate.getUserOrders());
		String encodedPassword = "";
		if(user.getPassword().isEmpty()) {
			encodedPassword = authUser.getPassword();
			
		}else {
			encodedPassword = encoder.encode(user.getPassword());
		}
		userToUpdate.setPassword(encodedPassword);
		userService.updateUser(userToUpdate.getId(),userToUpdate);
		authUser.setUsername(userToUpdate.getUsername());
		authUser.setEmail(userToUpdate.getEmail());
		customerProfile(authUser, model);
		return "redirect:/customer-profile";
	}
	
	@PostMapping("/employee-profile/edit")
	public String updateEmployeeInfo(@AuthenticationPrincipal UserDetailsImpl authUser,@ModelAttribute("user") User user, Model model) {
		User userToUpdate = userService.getByUserId(authUser.getUserId());
		userToUpdate.setEmail(user.getEmail() != null ? user.getEmail() : userToUpdate.getEmail());
		userToUpdate.setUsername(user.getUsername() != null ? user.getUsername() : userToUpdate.getUsername());
		userToUpdate.setUserOrders(userToUpdate.getUserOrders());
		String encodedPassword = "";
		if(user.getPassword().isEmpty()) {
			encodedPassword = authUser.getPassword();
			
		}else {
			encodedPassword = encoder.encode(user.getPassword());
		}
		userToUpdate.setPassword(encodedPassword);
		userService.updateUser(userToUpdate.getId(),userToUpdate);
		authUser.setUsername(userToUpdate.getUsername());
		authUser.setEmail(userToUpdate.getEmail());
		employeeProfile(authUser, model);
		return "redirect:/employee-profile";
	}
	
	@GetMapping("/orders-to-handle")
	public String getOrdersToHandle(Model model, Order order) {
		model.addAttribute("orders", orderService.getOrdersToHandle());
		return "orders-to-handle";
	}
	
	@PostMapping("/orders-to-handle/{id}")
	public String handleOrder(@PathVariable("id") Long id, Order order) {
		Order realOrder = orderService.findOrderById(id).get();
		realOrder.setOrderSent(order.isOrderSent());
		orderService.updateOrder(id, realOrder);
		return "redirect:/orders-to-handle";
	}
}
