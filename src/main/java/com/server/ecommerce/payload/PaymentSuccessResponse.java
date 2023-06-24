package com.server.ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSuccessResponse {
	private String paymentStatus;
	private String user_order_id;
	private boolean capture=false;

}
