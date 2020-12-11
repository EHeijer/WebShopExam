package com.edheijer.WebShopExam.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edheijer.WebShopExam.models.User;
import com.edheijer.WebShopExam.repositories.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public Optional<User> getByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public Optional<User> getById(Long id) {
		return userRepository.findById(id);
	}

	public User registerUser(User user) {
		return userRepository.saveAndFlush(user);
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
	
	public User getUserAndFetchOrders(Long id) {
		return userRepository.findUserAndFetchOrders(id);
	}
	
	public Boolean usernameAlreadyExists(String username) {
		return userRepository.existsByUsername(username);
	}
	
	public Boolean emailAlreadyExists(String email) {
		return userRepository.existsByUsername(email);
	}
}
