package com.edheijer.WebShopExam.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edheijer.WebShopExam.models.Role;
import com.edheijer.WebShopExam.models.RoleEnum;
import com.edheijer.WebShopExam.repositories.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public Optional<Role> getRoleByName(RoleEnum name){
		return roleRepository.findByName(name);
	}
	
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}
	
	public Optional<Role> getRoleById(Long id) {
		return roleRepository.findById(id);
	}
}
