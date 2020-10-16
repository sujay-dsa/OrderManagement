package com.keto.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.keto.login.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
   
    
}