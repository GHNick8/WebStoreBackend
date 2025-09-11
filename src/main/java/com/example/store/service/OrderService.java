package com.example.store.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.store.dto.CreateOrderRequest;
import com.example.store.dto.OrderResponse;
import com.example.store.entity.Order;
import com.example.store.entity.OrderItem;
import com.example.store.entity.Product;
import com.example.store.entity.User;
import com.example.store.repository.OrderRepository;
import com.example.store.repository.ProductRepository;
import com.example.store.repository.UserRepository;

@Service
public class OrderService {
  private final UserRepository users;
  private final ProductRepository products;
  private final OrderRepository orders;

  public OrderService(UserRepository users, ProductRepository products, OrderRepository orders) {
    this.users = users; this.products = products; this.orders = orders;
  }

  @Transactional
  public OrderResponse create(String email, CreateOrderRequest req) {
    if (req == null || req.items() == null || req.items().isEmpty())
      throw new IllegalArgumentException("Cart is empty");

    User user = users.findByEmail(email).orElseThrow();

    Order order = new Order();
    order.setUser(user);
    order.setStatus("PENDING");

    BigDecimal total = BigDecimal.ZERO;
    List<OrderItem> items = new ArrayList<>();

    for (CreateOrderRequest.Item it : req.items()) {
      Product p = products.findById(it.productId())
          .orElseThrow(() -> new IllegalArgumentException("Product not found: " + it.productId()));

      Integer q = it.quantity();
      int qty = (q == null || q < 1) ? 1 : q;

      if (p.getStock() < qty)
        throw new IllegalArgumentException("Insufficient stock for: " + p.getName());

      p.setStock(p.getStock() - qty);
      products.save(p);

      OrderItem oi = new OrderItem();
      oi.setOrder(order);
      oi.setProduct(p);
      oi.setQuantity(qty);
      oi.setPrice(p.getPrice()); 
      items.add(oi);

      total = total.add(p.getPrice().multiply(BigDecimal.valueOf(qty)));
    }

    order.setItems(items);
    order.setTotal(total);
    Order saved = orders.save(order);

    return toResponse(saved);
  }

  @Transactional(readOnly = true)
  public List<OrderResponse> myOrders(String email) {
    Long uid = users.findByEmail(email).orElseThrow().getId();
    return orders.findByUserId(uid).stream().map(this::toResponse).toList();
  }

  private OrderResponse toResponse(Order o) {
    List<OrderResponse.Item> its = o.getItems().stream().map(oi ->
      new OrderResponse.Item(
        oi.getProduct().getId(),
        oi.getProduct().getName(),
        oi.getQuantity(),
        oi.getPrice()
      )).toList();

    return new OrderResponse(o.getId(), o.getStatus(), o.getTotal(), o.getCreatedAt(), its);
  }
}