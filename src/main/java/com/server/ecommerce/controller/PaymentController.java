package com.server.ecommerce.controller;

import com.server.ecommerce.dto.OrderDto;
import com.server.ecommerce.payload.PaymentOrderResponse;
import com.server.ecommerce.payload.PaymentSuccessResponse;
import com.server.ecommerce.service.OrderService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private OrderService orderService;
	
	//create Order
	@PostMapping("/create")
	public PaymentOrderResponse createOrder(@RequestParam("price") int price) throws RazorpayException{
		
		RazorpayClient client=new RazorpayClient("rzp_test_SJbSE1ULGg8Kqg","CY3E379plxtIUuQhomelNMVq");
		
		JSONObject option=new JSONObject();
		option.put("amount",price*100);
		option.put("currency","INR");
		option.put("receipt","vk123");
		Order order = client.Orders.create(option);
		System.out.println(order);
		
		PaymentOrderResponse paymentOrder = new PaymentOrderResponse();
		paymentOrder.setMessage("CREATED");
		//paymentOrder.setPrice(order.get("amount")+"");
		paymentOrder.setOrderId(order.get("id"));
		paymentOrder.setOrderInformation("order just create from razopay server!!");
		return paymentOrder;
	}
	
	//capture payment method
	@PostMapping("/success")
	public PaymentSuccessResponse capturePayment(
			@RequestParam("razorpay_payment_id") String razorpay_payment_id,
			@RequestParam("razorpay_order_id") String razorpay_order_id,
			@RequestParam("razorpay_signature") String razorpay_signature,
			@RequestParam("user_order_id") int user_order_id
			) {
		
		//update the order => change to order Status to Success 
 		OrderDto dto = new OrderDto();
 		dto.setPaymentStatus("PAID");
 		this.orderService.updateOrder(user_order_id, dto);
 		
		PaymentSuccessResponse  paymentSuccess = new PaymentSuccessResponse();
		paymentSuccess.setUser_order_id(user_order_id+"");
		paymentSuccess.setCapture(true);
		paymentSuccess.setPaymentStatus("PAID");
		
		return paymentSuccess;
	}
	
	
	
	
}
