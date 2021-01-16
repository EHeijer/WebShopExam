package com.edheijer.WebShopExam.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.edheijer.WebShopExam.dto.OrderDTO;
import com.edheijer.WebShopExam.dto.ProductDTO;
import com.edheijer.WebShopExam.models.Order;
import com.edheijer.WebShopExam.models.Product;
import com.edheijer.WebShopExam.security.JwtResponse;
import com.edheijer.WebShopExam.security.JwtUtils;
import com.edheijer.WebShopExam.security.LoginRequest;
import com.edheijer.WebShopExam.security.UserDetailsServiceImpl;
import com.edheijer.WebShopExam.services.OrderService;
import com.edheijer.WebShopExam.services.ProductService;

@RestController
@RequestMapping("/api/")
@CrossOrigin
public class EmployeeRestController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		
		Authentication authentication;
		
		 try {
			 	authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
	        } catch (BadCredentialsException e) {
	            return ResponseEntity.badRequest().body("Sorry, we couldn't find an account with that password");
	        }
		
		 SecurityContextHolder.getContext().setAuthentication(authentication);
		 String jwt = jwtUtils.generateJwtToken(authentication);
		
		 UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
		 List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		 if(roles.contains("ADMIN") || roles.contains("EMPLOYEE")) {
			return ResponseEntity.ok(new JwtResponse(jwt));
		 }else {
			return ResponseEntity.badRequest().body("Your account lacks authorization to sign in here, contact an administrator");
		 }
	}
	
	@GetMapping("employee-actions/orders")
	public List<OrderDTO> getOrderToHandle(){
		return orderService.getAllOrders();
	}
	
	
	@PutMapping(path = "employee-actions/handle-orders/{id}")
	public void updateOrder(@PathVariable("id") int id, @RequestBody OrderDTO order) {
		orderService.updateOrder(Integer.toUnsignedLong(id), order);
	}
	
	@GetMapping("employee-actions/products")
	public List<ProductDTO> getProducts(){
		return productService.getAllProducts();
	}
	
	@PutMapping(path = "employee-actions/products/{id}")
	public void updateProduct(@PathVariable("id") int id, @RequestBody ProductDTO product) {
		productService.updateProduct(Integer.toUnsignedLong(id), product);
	}
	
	@PostMapping(path = "employee-actions/products")
	public void addProduct(@RequestBody ProductDTO product) {
		productService.addProduct(product);
	}
}
