package com.example.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.store.dto.CreateOrderRequest;
import com.example.store.dto.OrderResponse;
import com.example.store.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final OrderService service;

  public OrderController(OrderService service) { this.service = service; }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody CreateOrderRequest req, Authentication auth) {
    try {
      String email = auth.getName(); 
      OrderResponse resp = service.create(email, req);
      return ResponseEntity.ok(resp);
    } catch (IllegalArgumentException ex) {
      return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }
  }

  @GetMapping("/me")
  public List<OrderResponse> myOrders(Authentication auth) {
    return service.myOrders(auth.getName());
  }
}
