package com.keto.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.keto.login.model.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
   

}