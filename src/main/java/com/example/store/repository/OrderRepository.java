package com.example.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.store.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findByUserId(Long userId);
}
