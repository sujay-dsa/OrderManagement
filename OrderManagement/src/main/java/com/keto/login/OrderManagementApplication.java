package com.keto.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OrderManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderManagementApplication.class, args);
    }
}
