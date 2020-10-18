package com.keto.login.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.keto.login.model.Orders;
import com.keto.login.model.Product;
import com.keto.login.service.UserService;

@RestController
public class OrderController {
	
	@Autowired
	UserService userService;
	
	

    @GetMapping(value="/api/createorder")
    public String createDummyOrder() {
    	List<Product> productList = userService.getProducts();
    	Product duplicateProduct = userService.getProducts().get(0);
    	productList.add(duplicateProduct);
    	Double totalPrice = productList.stream().mapToDouble(x -> x.getPrice()).sum();
		Orders orders = Orders.builder()
    					.products(productList)
    					.orderDetais("dummy order")
    					.orderDate(new Timestamp(System.currentTimeMillis()))
    					.amount(totalPrice)
    					.build();//
		userService.saveDummyOrder(orders);
    			
    	return "success";
    }
	
    @GetMapping(value="/restaurant/download")
    public void downloadOrders(HttpServletResponse response){
    	File reportFile = userService.generateOrderReport();
    	try(InputStream is = new FileInputStream(reportFile)) {
    		response.setContentType("text/csv");
    		response.addHeader("Content-Disposition", "attachment;filename="+reportFile.getName());
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
    	} catch (IOException e1) {
    		throw new RuntimeException("IOError writing file to output stream");
		}
    	
    }
 
    
}
