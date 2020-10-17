package com.keto.login.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.keto.login.model.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
   
	@Override
	@Cacheable("ordersCache")
	List<Orders> findAll();
}