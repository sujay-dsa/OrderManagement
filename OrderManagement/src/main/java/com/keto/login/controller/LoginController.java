package com.keto.login.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.keto.login.model.Orders;
import com.keto.login.model.Product;
import com.keto.login.model.User;
import com.keto.login.service.UserService;

@Controller
public class LoginController {
	@Autowired
    private UserService userService;
    
    

    @GetMapping(value={"/", "/login"})
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @GetMapping(value="/registration")
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUserName(user.getUserName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the user name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @GetMapping(value="/restaurant/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        
        List<Orders> orders = userService.findOrdersByUser(user.getId());
       	modelAndView.addObject("orders", orders);
       	modelAndView.addObject("grandTotal", orders.stream().mapToDouble(order->order.getAmount()).sum());
        modelAndView.addObject("userName", "User: "+user.getUserName());
        modelAndView.addObject("adminMessage","Order Details");
        modelAndView.setViewName("restaurant/home");
        return modelAndView;
    }
    
    
    @GetMapping(value="/restaurant/orderdetails")
    public ModelAndView orderDetails(@RequestParam String orderId){
        List<Product> orderDetails = userService.getOrderDetails(orderId);
        ModelAndView modelAndView = new ModelAndView();
        
        
        Map<Product, Long> counted = orderDetails.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        
        modelAndView.addObject("productQty",counted);
        modelAndView.setViewName("restaurant/orderdetails");
        return modelAndView;
    }
    



}
