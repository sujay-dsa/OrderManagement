package com.keto.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.keto.login.model.Orders;
import com.keto.login.model.Product;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByProductsIn(List<Product> productList);
}