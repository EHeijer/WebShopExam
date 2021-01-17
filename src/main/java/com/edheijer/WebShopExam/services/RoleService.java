package com.edheijer.WebShopExam.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edheijer.WebShopExam.dto.RoleDTO;
import com.edheijer.WebShopExam.models.Role;
import com.edheijer.WebShopExam.models.RoleEnum;
import com.edheijer.WebShopExam.repositories.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired RoleMapper roleMapper;
	
	public RoleDTO getRoleByName(RoleEnum name){
		return roleMapper.toDto(roleRepository.findByName(name));
	}
	
	public List<RoleDTO> getAllRoles() {
		return roleMapper.toDto(roleRepository.findAll());
	}
	
	public Optional<Role> getRoleById(Long id) {
		return roleRepository.findById(id);
	}
}
