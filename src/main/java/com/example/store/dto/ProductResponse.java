package com.example.store.dto;

import java.math.BigDecimal;

public record ProductResponse(
    Long id,
    String name,
    String description,
    BigDecimal price,
    BigDecimal originalPrice,
    boolean onSale,
    int stock,
    String imageUrl
) {}
