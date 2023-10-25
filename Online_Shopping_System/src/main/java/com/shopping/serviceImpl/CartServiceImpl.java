package com.shopping.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.exception.ResourceNotFoundException;
import com.shopping.model.Cart;
import com.shopping.model.Customer;
import com.shopping.model.Product;
import com.shopping.repository.CartRepository;
import com.shopping.service.CartService;
import com.shopping.service.CustomerService;
import com.shopping.service.ProductService;



@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	public CartRepository cartRepository;
	
//	@Autowired
//	public ProductRepository productRepository;

	@Autowired
	public ProductService productService;
	
	@Autowired
	public CustomerService customerService;
	
public CartServiceImpl(CartRepository cartRepository) {
		super();
		this.cartRepository = cartRepository;
	}

@Override
public Cart addCart(Cart cart,long productId,long customerId) {

	Product product =productService.getProductByProductId(productId) ;
	Customer customer =customerService.getCustomerById(customerId) ;
	 List<Cart> crl = this.getAllCarts();
	 int flag = 0;
	 Cart existingCart = null;
	 if (crl.size() > 0) {
		 for (int i=0;i< crl.size();i++) {
			 Cart c = this.getCartById(crl.get(i).getCartId());
			 if (c.getCustomer().getCustomerId() == customerId && c.getProduct().getProductId() == productId) {
				 flag = 1;
				 existingCart = c;
			 }
		 }
	 }
	 product.setQuantity(product.getQuantity()-cart.getQuantity());
	 if (flag ==1 && existingCart != null) {
		 existingCart.setQuantity(existingCart.getQuantity() + cart.getQuantity());
		 existingCart.setMrpPrice(product.getMrpPrice());
		existingCart.setCustomer(customer);
		System.out.println("111111111111111111111111111111111");
		return this.updateCart(existingCart, existingCart.getCartId());
	 } else {
		 cart.setProduct(product);
		cart.setMrpPrice(product.getMrpPrice());
		cart.setCustomer(customer);
		System.out.println("2222222222222222222222222222222222222222");
		return cartRepository.save(cart);
	 }
}



@Override
public List<Cart> getAllCarts() {
	return cartRepository.findAll();
}



@Override
public Cart getCartById(long cartId) {
	
	return cartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart","Id",cartId));
}



@Override
public Cart updateCart(Cart cart, long cartId) {
	Cart existingCart=cartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart","Id",cartId));
	existingCart.setQuantity(cart.getQuantity());
	//existingCart.setPrice(cart.getPrice());
	existingCart.setMrpPrice(cart.getMrpPrice());
	//existingCart.setImage(cart.getImage());
	existingCart.setCartId(cart.getCartId());
	existingCart.setProduct(cart.getProduct());
	//existingCart.setCustomerId(cart.getCustomerId());
	existingCart.setCustomer(cart.getCustomer());
    cartRepository.save(existingCart);
    
	return existingCart;
}



@Override
public void deleteCart(long cartId) {
	Cart existingCart=cartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart","Id",cartId));
	Product product =productService.getProductByProductId(existingCart.getProduct().getProductId());
	product.setQuantity(product.getQuantity() + existingCart.getQuantity());
	productService.updateProduct(product, product.getProductId());
	cartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart","Id",cartId));
	cartRepository.deleteById(cartId);
	
}

@Override
public void deleteCartByCustomer(Customer c) {
	cartRepository.deleteCartByCustomer(c);
	
}

}
