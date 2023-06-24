package com.server.ecommerce.dto;

import com.server.ecommerce.model.Order;
import com.server.ecommerce.model.Product;
import com.server.ecommerce.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
	private int orderItemId;
	private Product product;
	private double totalProductPrice;
	private int quantity;
	Order order;
	private User user;
}
