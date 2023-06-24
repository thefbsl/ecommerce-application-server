package com.server.ecommerce.repository;

import com.server.ecommerce.model.Cart;
import com.server.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Integer> {
	 Optional<Cart> findByUser(User user);

}
