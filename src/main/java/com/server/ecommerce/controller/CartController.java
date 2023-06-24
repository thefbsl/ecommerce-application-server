package com.server.ecommerce.controller;

import com.server.ecommerce.dto.CartDto;
import com.server.ecommerce.payload.ItemRequest;
import com.server.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	CartService cartservice;
	@PostMapping("/")
	public ResponseEntity<CartDto> addItem(@RequestBody ItemRequest itemRequest,Principal principal) {
		return new ResponseEntity<>(cartservice.addItem(itemRequest,principal.getName()), HttpStatus.OK);
	}
	@GetMapping("/")
	public ResponseEntity<CartDto> getCart(Principal principal){
		return new ResponseEntity<>(cartservice.getCart(principal.getName()),HttpStatus.OK);
	}
	@PutMapping("/{productId}")
	public ResponseEntity<CartDto> removeProduct(@PathVariable int  productId,Principal principal){
		return new ResponseEntity<>(cartservice.removeCartItem(principal.getName(), productId),HttpStatus.ACCEPTED);
	}
}
