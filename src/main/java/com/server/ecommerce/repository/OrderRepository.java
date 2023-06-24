package com.server.ecommerce.repository;

import com.server.ecommerce.model.Order;
import com.server.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer>{
  Optional<List<Order>>findByUser(User findByEmail);
}
