package com.edheijer.WebShopExam.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "orders")
@EqualsAndHashCode(exclude="user")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate dateCreated = LocalDate.now();
	private boolean isOrderSent = false;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
	private List<OrderLine> orderLines = new ArrayList<>();
	
	@Transient
	public Double getTotalOrderPrice() {
		double sum = 0D;
		List<OrderLine> orderLines = getOrderLines();
		
		for(OrderLine ol : orderLines) {
			sum += ol.getSumOfOrderLine();
		}
		return sum;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", dateCreated=" + dateCreated + "]";
	}

	
}
