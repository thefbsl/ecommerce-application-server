package com.server.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cartId;
	@JsonManagedReference
	@OneToMany(mappedBy = "cart" ,cascade=CascadeType.ALL,orphanRemoval = true)
	private Set<CartItem> items = new HashSet<>();
	@OneToOne
	private  User user;


}
