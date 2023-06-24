package com.server.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="ecom_order")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;
	private String orderStatus;
	private String paymentStatus;	
	private Date orderCreated;
	private Double orderAmount;
	private String billingAddress;
	private Date orderDelivered;
	@OneToOne
	private User user;
	@JsonManagedReference
	@OneToMany(mappedBy="order",cascade = CascadeType.ALL)
    private	Set<OrderItem> items = new HashSet<>();
}
