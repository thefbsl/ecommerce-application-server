package com.server.ecommerce.service.implementation;

import com.server.ecommerce.dto.CartDto;
import com.server.ecommerce.exception.ResourceNotFoundException;
import com.server.ecommerce.model.Cart;
import com.server.ecommerce.model.CartItem;
import com.server.ecommerce.model.Product;
import com.server.ecommerce.model.User;
import com.server.ecommerce.payload.ItemRequest;
import com.server.ecommerce.repository.CartRepository;
import com.server.ecommerce.repository.ProductRepository;
import com.server.ecommerce.repository.UserRepository;
import com.server.ecommerce.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
public class CartServiceImp implements CartService {
	@Autowired
	private ProductRepository productRepo;
	@Autowired
    private	UserRepository userRepo;
	@Autowired
	private	CartRepository catRepo;
	@Autowired
	ModelMapper mapper;
	@Override
	public CartDto addItem(ItemRequest item, String UserName) {
		int productId = item.getProductId();
		int productQuantity = item.getQuantity();
		User user= userRepo.findByEmail(UserName).orElseThrow(() -> new ResourceNotFoundException("User not Found"));
		Product product= productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not Found"));
		if(!product.isStock()){
			new ResourceNotFoundException("Stock is out of Stock");
		}
		      
		// create cartItem with product id and Quntity
		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantity(productQuantity);
		cartItem.setTotalProductPrice();
		      
		// Getting CartItem from User
		Cart cart = user.getCart();
	
		if(cart == null) {
			cart = new Cart();
			cart.setUser(user);
		}

		//add items in cart
		cartItem.setCart(cart);
		Set<CartItem> items = cart.getItems();
		         
		//items.add(cartItem);
		         
		/*here we Check Item is available in Item table or not
		  * if item is available then we Increase Quantity else
		  * add new item
		*/
		AtomicReference<Boolean> flag = new AtomicReference<>(false);
	    Set<CartItem> newitem = items.stream().map((i) -> {
			if(i.getProduct().getProductId()==product.getProductId()) {
				i.setQuantity(productQuantity);
				i.setTotalProductPrice();
				flag.set(true);
			}
			return i;
		}).collect(Collectors.toSet());
		        
		if(flag.get()) {
			items.clear();
			items.addAll(newitem);

		}
		else {
			cartItem.setCart(cart);
			items.add(cartItem);
		}
		// itemss.addAll(items);
		Cart updateCart = catRepo.save(cart);
		return mapper.map(updateCart,CartDto.class);
	}

	@Override
	public CartDto getCart(String UserName) {
		User user = userRepo.findByEmail(UserName).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		Cart cart = catRepo.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Cart Not found"));
		return mapper.map(cart,CartDto.class) ;
	}

	@Override
	public CartDto removeCartItem(String UserName, int productId) {
		User user = userRepo.findByEmail(UserName).orElseThrow(() -> new ResourceNotFoundException("User not Found"));
		Cart cart=user.getCart();
		Set<CartItem> items = cart.getItems();
		boolean removeIf = items.removeIf((item) ->item.getProduct().getProductId() == productId);
		Cart updatecart = catRepo.save(cart);
		return mapper.map(updatecart,CartDto.class);
	}
}
