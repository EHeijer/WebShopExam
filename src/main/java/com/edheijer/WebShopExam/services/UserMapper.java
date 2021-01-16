package com.edheijer.WebShopExam.services;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.edheijer.WebShopExam.dto.UserDTO;
import com.edheijer.WebShopExam.models.Order;
import com.edheijer.WebShopExam.models.User;

@Mapper(componentModel = "spring", uses = {OrderMapper.class, RoleMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, User>{

	@Mapping(source = "roles", target = "roleDTOList")
	@Mapping(source = "userOrders", target = "orderDTOList")
	UserDTO toDto(User user);
	
	@Mapping(source = "roleDTOList", target = "roles")
	@Mapping(source = "orderDTOList", target = "userOrders")
	User toEntity(UserDTO userDTO);
	
	default User fromId(Long id) {
		if(id == null) {
			return null;
		}
		User user= new User();
		user.setId(id);
		return user;
	}
}
