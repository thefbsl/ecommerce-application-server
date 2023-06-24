package com.server.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cartItemId;
	@ManyToOne
	@JsonBackReference
    private Cart cart;
	private int quantity;
	private double totalProductPrice;
	@OneToOne
	private Product product;
	public void setTotalProductPrice() {
		this.totalProductPrice = this.quantity * this.product.getProductPrice();
	}
}
