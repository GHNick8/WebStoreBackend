package com.example.store.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class OrderItem {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @ManyToOne(optional=false) 
    private Order order;

    @ManyToOne(optional=false) 
    private Product product;

    @Column(nullable=false) 
    private Integer quantity;

    @Column(precision=10, scale=2) 
    private BigDecimal price;
    
}
