package com.server.ecommerce.service.implementation;

import com.server.ecommerce.dto.OrderDto;
import com.server.ecommerce.exception.ResourceNotFoundException;
import com.server.ecommerce.model.*;
import com.server.ecommerce.payload.OrderRequest;
import com.server.ecommerce.repository.CartRepository;
import com.server.ecommerce.repository.OrderRepository;
import com.server.ecommerce.repository.UserRepository;
import com.server.ecommerce.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
@Service
public class OrderServiceImp implements OrderService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private  ModelMapper mapper;
	

	@Override
	public OrderDto create(OrderRequest request,String Username) {
	    User findByEmail = userRepository.findByEmail(Username).
				orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
	  	int cartId=request.getCartID();
	    Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart id not found"));
	    Set<CartItem> items= cart.getItems();
	    Order order=new Order();
	    
	    AtomicReference<Double> totalOrderPrice= new AtomicReference<>(0.0);
	    Set<OrderItem> orderItems = items.stream().map((cartItem) ->{
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setTotalProductPrice(cartItem.getTotalProductPrice());
			orderItem.setOrder(order);
			totalOrderPrice.set(totalOrderPrice.get()+orderItem.getTotalProductPrice());
			int productId = orderItem.getProduct().getProductId();
	    	return orderItem;
	    }).collect(Collectors.toSet());
		order.setItems(orderItems);
		String address = request.getAddress();
		order.setBillingAddress(address);
		order.setPaymentStatus("Not Piad");
		order.setOrderCreated(new Date());
		order.setOrderDelivered(null);
		order.setOrderStatus("Created");
		order.setUser(findByEmail);
		order.setOrderAmount(totalOrderPrice.get());

		if(order.getOrderAmount() > 0) {
			Order savedorder = orderRepository.save(order);
			cart.getItems().clear();
			cartRepository.save(cart);
			return mapper.map(savedorder,OrderDto.class) ;
		}else {
			throw new ResourceNotFoundException("Pls Add Cart First and place Order");
		}
	}

	@Override
	public List<OrderDto> getAllOrder(String p) {
	    User findByEmail = userRepository.findByEmail(p).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
		List<Order> findAll = orderRepository.findByUser(findByEmail).orElseThrow(()->new ResourceNotFoundException("Order Not Found"));

		return findAll.stream().map((each) -> mapper.map(each,OrderDto.class)).collect(Collectors.toList());
	}

	@Override
	public OrderDto getOrder(int OrderId) {
		Order order = orderRepository.findById(OrderId)
				.orElseThrow(()->new ResourceNotFoundException("Order not Found"));
		return mapper.map(order,OrderDto.class);
	}

	@Override
	public void deleteOrder(int orderId) {
		Order findById = orderRepository.findById(orderId)
				.orElseThrow(()->new ResourceNotFoundException("Order not Found"));
		 orderRepository.delete(findById);
	}

	@Override
	public OrderDto updateOrder(int orderId, OrderDto orderDto) {
		Order findOrder = orderRepository.findById(orderId)
				.orElseThrow(()->new ResourceNotFoundException("order not Found"));
		findOrder.setOrderDelivered(orderDto.getOrderDelivered());
		findOrder.setPaymentStatus(orderDto.getPaymentStatus());
		findOrder.setOrderStatus(orderDto.getOrderStatus());
		Order save = orderRepository.save(findOrder);
		return mapper.map(save,OrderDto.class);
	}

	@Override
	public List<OrderDto> listAllOrder() {
		List<Order> listOfAllOrders = orderRepository.findAll();
		return listOfAllOrders.stream()
				 .map((each)-> mapper.map(each,OrderDto.class)).collect(Collectors.toList());
	}
}
