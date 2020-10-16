package com.keto.login.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
	


    @Id
    @Column(name = "product_id")
    private Integer id;
    
    @Column(name = "product_details")
    @NotEmpty(message = "*Please provide a product name")
    private String productDetails;
    

    
    @Column(name = "price")
    @NotEmpty(message = "*Please provide product price")
    private Double price;
    
    
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private User restaurant;
    
    
    
    
}
