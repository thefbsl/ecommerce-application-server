package com.server.ecommerce.model;

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
@Table(name="products")   //@Table we can table name
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int    productId;
	@Column(name="product_name",length=300,unique=true)
	private String productName;
	private String productDesc;
	private double productPrice;
	private boolean stock=true;
	private int productQuantity;
	private boolean live;
	private String imageName;
	@ManyToOne(fetch=FetchType.EAGER)
	private Category category;
}
