package com.server.ecommerce.dto;

import com.server.ecommerce.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
	private int orderId;
	private String orderStatus;
	private String paymentStatus;	
	private Date orderCreated;
	private Double orderAmount;
	private String billingAddress;
	private Date orderDelivered;
	private	Set<OrderItem> items = new HashSet<>();
	private UserDto user;
}
