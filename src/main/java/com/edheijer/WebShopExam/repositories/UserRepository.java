package com.edheijer.WebShopExam.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edheijer.WebShopExam.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
	
	Optional<User> findById(Long id);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
	@Query("SELECT user FROM User user LEFT JOIN FETCH user.userOrders WHERE user.id=:id")
	User findUserAndFetchOrders(@Param("id") Long id);
}
