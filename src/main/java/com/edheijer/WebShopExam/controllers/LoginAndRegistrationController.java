package com.edheijer.WebShopExam.controllers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.edheijer.WebShopExam.dto.UserDTO;
import com.edheijer.WebShopExam.models.Role;
import com.edheijer.WebShopExam.models.RoleEnum;
import com.edheijer.WebShopExam.models.User;
import com.edheijer.WebShopExam.repositories.RoleRepository;
import com.edheijer.WebShopExam.services.UserService;

@Controller
public class LoginAndRegistrationController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/register")
	public String showRegisterForm(Model model, UserDTO user) {
		model.addAttribute("user", new UserDTO());
		model.addAttribute("usernameExist", userService.usernameAlreadyExists(user.getUsername()));
		return "register";
	}
	
	@PostMapping("/register")
	public String userRegistration(UserDTO user, Model model) {
		model.addAttribute("user", new UserDTO());
		if(userService.usernameAlreadyExists(user.getUsername())) {
			model.addAttribute("usernameExist", userService.usernameAlreadyExists(user.getUsername()));
			return "register";
		}  
		userService.registerUser(user);
		return "register_success";
		
	}
	

}
