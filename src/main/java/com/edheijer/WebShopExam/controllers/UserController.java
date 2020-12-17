package com.edheijer.WebShopExam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.edheijer.WebShopExam.models.User;
import com.edheijer.WebShopExam.security.UserDetailsImpl;
import com.edheijer.WebShopExam.services.ShoppingCartService;
import com.edheijer.WebShopExam.services.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@GetMapping("/customer-profile")
	public String customerProfile(@AuthenticationPrincipal UserDetailsImpl authUser, Model model) {
		User user = userService.getUserAndFetchOrders(authUser.getUserId());
		model.addAttribute("user", user);
		model.addAttribute("orders", user.getUserOrders());
		model.addAttribute("cartSize", shoppingCartService.getProductsInCart().size());
		return "customer-profile";
	}
	
	@GetMapping("/employee-profile")
	public String employeeProfile(@AuthenticationPrincipal UserDetailsImpl user, Model model) {
		model.addAttribute("user", user);
		model.addAttribute("cartSize", shoppingCartService.getProductsInCart().size());
		return "employee-profile";
	}
	
	@GetMapping("/admin-portal")
	public String adminPortal(@AuthenticationPrincipal UserDetailsImpl user, Model model) {
		model.addAttribute("user", user);
		model.addAttribute("cartSize", shoppingCartService.getProductsInCart().size());
		return "admin-portal";
	}
	
	@GetMapping("/admin-portal/admin-profile")
	public String adminProfile(@AuthenticationPrincipal UserDetailsImpl user, Model model) {
		model.addAttribute("user", user);
		model.addAttribute("cartSize", shoppingCartService.getProductsInCart().size());
		return "admin-profile";
	}
	
	@PostMapping("/customer-profile/edit")
	public String updateCustomerInfo(@AuthenticationPrincipal UserDetailsImpl authUser,@ModelAttribute("user") User user, Model model) {
		updateUser(authUser, user);
		customerProfile(authUser, model);
		return "redirect:/customer-profile";
	}

	@PostMapping("/employee-profile/edit")
	public String updateEmployeeInfo(@AuthenticationPrincipal UserDetailsImpl authUser,@ModelAttribute("user") User user, Model model) {
		updateUser(authUser, user);
		employeeProfile(authUser, model);
		return "redirect:/employee-profile";
	}
	
	@PostMapping("/admin-profile/edit")
	public String updateAdminInfo(@AuthenticationPrincipal UserDetailsImpl authUser,@ModelAttribute("user") User user, Model model) {
		updateUser(authUser, user);
		employeeProfile(authUser, model);
		return "redirect:/admin-portal/admin-profile";
	}
	
	private void updateUser(UserDetailsImpl authUser, User user) {
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
	}
	
}
