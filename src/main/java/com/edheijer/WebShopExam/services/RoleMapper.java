package com.edheijer.WebShopExam.services;

import org.mapstruct.Mapper;

import com.edheijer.WebShopExam.dto.RoleDTO;
import com.edheijer.WebShopExam.models.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper extends EntityMapper<RoleDTO, Role>{
//	RoleDTO toDTO(Role role);
//	Role toEntity(RoleDTO roleDTO);
}
