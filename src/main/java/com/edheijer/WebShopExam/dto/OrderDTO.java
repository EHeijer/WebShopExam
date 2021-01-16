package com.edheijer.WebShopExam.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class OrderDTO {

	private Long id;
	private LocalDate dateCreated;
	private boolean orderSent;
	private Long userId;
	private List<OrderLineDTO> orderLineDTOList;
	private Double orderSum;
	
	
}
