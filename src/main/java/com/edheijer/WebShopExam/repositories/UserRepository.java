package com.edheijer.WebShopExam.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edheijer.WebShopExam.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);

}
