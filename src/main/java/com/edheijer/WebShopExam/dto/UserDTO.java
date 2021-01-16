package com.edheijer.WebShopExam.dto;

import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class UserDTO {

	private Long id;
	private String username;
	private String email;
	private boolean enabled;
	private Set<RoleDTO> roleDTOList;
	private List<OrderDTO> orderDTOList;
	private String password;
}
