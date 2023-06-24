package com.server.ecommerce.service;

import com.server.ecommerce.dto.CartDto;
import com.server.ecommerce.payload.ItemRequest;

public interface CartService {
	CartDto addItem(ItemRequest item,String UserName);
	CartDto getCart(String UserName);
	CartDto removeCartItem(String UserName,int productId);

}
