package com.edheijer.WebShopExam.controllers;

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
	public String employeeProfile() {
		return "employee-profile";
	}
	
	@GetMapping("/admin-profile")
	public String adminProfile() {
		return "admin-profile";
	}
	
	@PostMapping("/user/edit")
	public String updateUserInfo(@AuthenticationPrincipal UserDetailsImpl authUser,@ModelAttribute("user") User user, Model model) {
		System.out.println(authUser.getUserId());
		User userToUpdate = userService.getByUserId(authUser.getUserId());
		userToUpdate.setEmail(user.getEmail() != null ? user.getEmail() : userToUpdate.getEmail());
		userToUpdate.setUsername(user.getUsername() != null ? user.getUsername() : userToUpdate.getUsername());
		userToUpdate.setUserOrders(userToUpdate.getUserOrders());
		String encodedPassword = "";
		System.out.println(user.getPassword().isBlank());
		System.out.println(authUser.getPassword());
		if(user.getPassword().isBlank()) {
			encodedPassword = authUser.getPassword();
			
		}else {
			encodedPassword = encoder.encode(user.getPassword());
		}
		userToUpdate.setPassword(encodedPassword);
		userService.updateUser(userToUpdate.getId(),userToUpdate);
		authUser.setUsername(userToUpdate.getUsername());
		authUser.setEmail(userToUpdate.getEmail());
		
		return customerProfile(authUser, model);
	}
}
