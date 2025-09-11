package com.example.store.dto;

import java.util.List;

public record CreateOrderRequest(
  List<Item> items
) {
  public record Item(Long productId, Integer quantity) {}
}
