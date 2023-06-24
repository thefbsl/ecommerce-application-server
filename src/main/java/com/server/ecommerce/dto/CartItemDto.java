package com.server.ecommerce.dto;

import com.server.ecommerce.model.Cart;
import com.server.ecommerce.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
	private int cartItemId;
    private Cart cart;
	private int quantity;
	private double totalProductPrice;
	private Product product;


}
