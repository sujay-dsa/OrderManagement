package com.keto.login.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.keto.login.model.Orders;
import com.keto.login.model.Product;
import com.keto.login.model.Role;
import com.keto.login.model.User;
import com.keto.login.repository.OrdersRepository;
import com.keto.login.repository.ProductRepository;
import com.keto.login.repository.RoleRepository;
import com.keto.login.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private OrdersRepository ordersRepository;
    private ProductRepository productRepository;
    
    
    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       OrdersRepository ordersRepository,
                       ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.ordersRepository = ordersRepository;
        this.productRepository=productRepository;
       
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("RESTAURANT_ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }
    
    public List<Orders> findOrdersByUser(Integer user_id){
    	List<Orders> ordersList=ordersRepository.findAll();
		  List<Orders> restaurantOrders = ordersList .stream() .filter(order ->
		  matchOrderByRestaurant(order,user_id)) .collect(Collectors.toList());
    	return restaurantOrders;
    }

	private Boolean matchOrderByRestaurant(Orders order, Integer user_id) {
		// we can do this because all products of an order will come from same restaurant
		return (order.getProducts().get(0).getRestaurant().getId()== user_id);
	}
	
	public List<Product> getProducts(){
		
		return productRepository.findAll().stream().filter(prod -> prod.getRestaurant().getId()==2).collect(Collectors.toList());
	}

	public Orders saveDummyOrder(Orders order){
		
		return ordersRepository.save(order);
	}
	
	public List<Product> getOrderDetails(String orderId){
		List<Product> productsOrdered = ordersRepository.findById(Long.valueOf(orderId)).get().getProducts();
		return productsOrdered;
	}
	
	
}