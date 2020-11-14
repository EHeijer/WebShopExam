package com.edheijer.WebShopExam.services;

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

	public void registerUser(User user) {
		userRepository.saveAndFlush(user);
	}
	
	public void updateUser(Long id, User user) {
		userRepository.saveAndFlush(user);
	}
	
	public User getByUserId(Long id) {
		return userRepository.getOne(id);
	}
}
