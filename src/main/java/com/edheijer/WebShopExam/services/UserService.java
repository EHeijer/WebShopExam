package com.edheijer.WebShopExam.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edheijer.WebShopExam.dto.RoleDTO;
import com.edheijer.WebShopExam.dto.UserDTO;
import com.edheijer.WebShopExam.models.Role;
import com.edheijer.WebShopExam.models.RoleEnum;
import com.edheijer.WebShopExam.models.User;
import com.edheijer.WebShopExam.repositories.RoleRepository;
import com.edheijer.WebShopExam.repositories.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public Optional<User> getByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public Optional<User> getById(Long id) {
		return userRepository.findById(id);
	}

	public UserDTO registerUser(UserDTO userDTO) {
		String encodedPassword = encoder.encode(userDTO.getPassword());
		userDTO.setPassword(encodedPassword);
		userDTO.setEnabled(true);
		
		Set<RoleDTO> roles = new HashSet<>();
		Role userRole = roleRepository.findByName(RoleEnum.CUSTOMER);
		RoleDTO userRoleDTO = roleMapper.toDto(userRole);
		roles.add(userRoleDTO);
		userDTO.setRoleDTOList(roles);
		User user = userMapper.toEntity(userDTO);
		userRepository.saveAndFlush(user);
		return userMapper.toDto(user);
	}
	
	public void updateUser(Long id, User user) {
		userRepository.saveAndFlush(user);
	}
	
	public User getByUserId(Long id) {
		return userRepository.getOne(id);
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public UserDTO getUserAndFetchOrders(Long id) {
		User user = userRepository.findUserAndFetchOrders(id);
		return userMapper.toDto(user);
	}
	
	public Boolean usernameAlreadyExists(String username) {
		return userRepository.existsByUsername(username);
	}
	
	public Boolean emailAlreadyExists(String email) {
		return userRepository.existsByUsername(email);
	}
}
