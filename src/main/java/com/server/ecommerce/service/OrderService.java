package com.server.ecommerce.service;

import com.server.ecommerce.dto.OrderDto;
import com.server.ecommerce.payload.OrderRequest;

import java.util.List;

public interface OrderService {
 OrderDto create(OrderRequest request,String username);
 //Get OrderBy User
 List<OrderDto> getAllOrder(String p);
 //Get All Order
 List<OrderDto>listAllOrder();
 OrderDto getOrder(int OrderId);
 void deleteOrder(int orderId);
 OrderDto updateOrder(int orderId,OrderDto orderDto);

 

}
