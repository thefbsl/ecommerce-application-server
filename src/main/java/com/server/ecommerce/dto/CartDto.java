package com.server.ecommerce.dto;

import com.server.ecommerce.model.CartItem;
import com.server.ecommerce.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
	private int cartId;
	private Set<CartItem> items = new HashSet<>();
	@JsonIgnore
	private  User user;
}
