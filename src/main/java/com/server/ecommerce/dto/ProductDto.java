package com.server.ecommerce.dto;

import com.server.ecommerce.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	@NotEmpty
	@Size(min = 0,message="Id should be Greater than 0")
	private int productId;
	@Pattern(regexp="/^[A-Z]-[A-Z]{3,5}\\/[\\d]{2}\\/[\\d]{3}$/")
	private String productName;
	@NotEmpty
	private String productDesc;
	@Size(min=0,message="price Should be greater than 0")
	@NotEmpty
	private double productPrice;
	private boolean stock;
	@Size(min=1 ,message="Quantity must Should  be greater than 0")
	private int productQuantity;
	private boolean live;
	private String imageName;
	private Category category;
}
