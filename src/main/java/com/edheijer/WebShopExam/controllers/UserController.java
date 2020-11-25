package com.edheijer.WebShopExam.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl;
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
import com.edheijer.WebShopExam.models.Product;
import com.edheijer.WebShopExam.models.Role;
import com.edheijer.WebShopExam.models.RoleEnum;
import com.edheijer.WebShopExam.models.User;
import com.edheijer.WebShopExam.security.UserDetailsImpl;
import com.edheijer.WebShopExam.services.OrderService;
import com.edheijer.WebShopExam.services.ProductService;
import com.edheijer.WebShopExam.services.RoleService;
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
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/customer-profile")
	public String customerProfile(@AuthenticationPrincipal UserDetailsImpl authUser, Model model) {
		User user = userService.getUserAndFetchOrders(authUser.getUserId());
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
	
	@GetMapping("/admin-portal/users")
	public String viewAllUsers(Model model, User user) {
		
		
		model.addAttribute("customer", roleService.getRoleByName(RoleEnum.CUSTOMER).get());
		model.addAttribute("employee", roleService.getRoleByName(RoleEnum.EMPLOYEE).get());
		model.addAttribute("admin", roleService.getRoleByName(RoleEnum.ADMIN).get());
		model.addAttribute("roles", roleService.getAllRoles());
		model.addAttribute("role", new Role());
		model.addAttribute("users", userService.getAllUsers());
		return "users";
	}
	
	@PostMapping("/customer-profile/edit")
	public String updateCustomerInfo(@AuthenticationPrincipal UserDetailsImpl authUser,@ModelAttribute("user") User user, Model model) {
		User userToUpdate = userService.getById(authUser.getUserId()).get();
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
		User userToUpdate = userService.getById(authUser.getUserId()).get();
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
	
	@PostMapping("/admin-profile/edit")
	public String updateAdminInfo(@AuthenticationPrincipal UserDetailsImpl authUser,@ModelAttribute("user") User user, Model model) {
		User userToUpdate = userService.getById(authUser.getUserId()).get();
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
		return "redirect:/admin-portal/admin-profile";
	}
	
	@GetMapping("/admin-portal/view-products")
	public String getProductRegister(Model model, Product product) {
		model.addAttribute("products", productService.getAllProducts());
		return "view-products";
	}
	
	@GetMapping("/admin-portal/orderhistory")
	public String getOrderHistory(Model model, Order order) {
		model.addAttribute("orders", orderService.getAllOrders());
		return "orderhistory";
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
	
	@PostMapping("/users/disable/{id}")
	public String changeUserStatus(@PathVariable("id") Long id) {
		User userToUpdate = userService.getById(id).get();
//		userToUpdate.setRoles(!user.getRoles().isEmpty() ? user.getRoles() : userToUpdate.getRoles());
		if(userToUpdate.isEnabled() == true) {
			userToUpdate.setEnabled(false);
		}else {
			userToUpdate.setEnabled(true);
		}
		userService.updateUser(id, userToUpdate);
		return "redirect:/admin-portal/users";
	}
	
	@PostMapping("/users/access-level/{id}")
	public String changeAccessLevel(@PathVariable("id") Long id, @ModelAttribute("role") Role role) {
//		System.out.println(roleService.getRoleByName(RoleEnum.CUSTOMER).get());
		User userToUpdate = userService.getById(id).get();
		Role getRole = roleService.getRoleByName(role.getName()).get();
		Set<Role> userRoles = userToUpdate.getRoles();
		userRoles.clear();
		userRoles.add(getRole);
		System.out.println(userRoles);
		System.out.println(getRole);
//		userToUpdate.setRoles(!user.getRoles().isEmpty() ? user.getRoles() : userToUpdate.getRoles());
		userService.updateUser(id, userToUpdate);
		return "redirect:/admin-portal/users";
	}
	
}
