package com.example.store.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @Column(nullable=false) 
    private String name;

    private String description;

    @Column(nullable=false, precision=10, scale=2) 
    private BigDecimal price;

    private String imageUrl;

    @Column(nullable=false) 
    private Integer stock;

    @Column(nullable = false)
    private boolean onSale = false;

    @Column(nullable = true)
    private BigDecimal originalPrice;

}
