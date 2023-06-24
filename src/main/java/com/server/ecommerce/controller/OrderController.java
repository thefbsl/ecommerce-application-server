package com.server.ecommerce.controller;

import com.server.ecommerce.dto.OrderDto;
import com.server.ecommerce.payload.ApiResponse;
import com.server.ecommerce.payload.OrderRequest;
import com.server.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderService orderService;

	@PostMapping("/")
	public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest request,Principal principal){
	   return new ResponseEntity<>(orderService.create(request, principal.getName()),HttpStatus.CREATED);
	}
	
	//Getting all Order from user
	@GetMapping("/")
	public ResponseEntity<List<OrderDto>>getOrderByUser(Principal p){
		return new ResponseEntity<>(orderService.getAllOrder(p.getName()),HttpStatus.OK);
	}
	
	//Getting all Order 
	@GetMapping("/list")
	public ResponseEntity<List<OrderDto>>getAllOrder(){
		return new ResponseEntity<>(orderService.listAllOrder(),HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> delete(@PathVariable int id){
		orderService.deleteOrder(id);
		return new ResponseEntity<>(new ApiResponse("Order Deleted",true),HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ADMIN')")   
	@PutMapping("/{id}")
	public ResponseEntity<OrderDto>update(@Valid @PathVariable int id,@RequestBody OrderDto orderDto){
		return new ResponseEntity<>(orderService.updateOrder(id, orderDto),HttpStatus.OK);
	}
	
	@GetMapping("/s/{id}")
    public ResponseEntity<OrderDto>getByOrderId(@PathVariable int id){
	    return new ResponseEntity<OrderDto>(orderService.getOrder(id),HttpStatus.OK);
    }

}
