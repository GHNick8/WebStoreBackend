package com.example.store.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.store.dto.ProductResponse;
import com.example.store.entity.Product;
import com.example.store.repository.ProductRepository;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/products")
public class ProductController {
  private final ProductRepository repo;
  public ProductController(ProductRepository repo){ this.repo = repo; }

  @GetMapping
  public List<ProductResponse> getAll() {
      return repo.findAll().stream()
          .map(p -> new ProductResponse(
              p.getId(),
              p.getName(),
              p.getDescription(),
              p.getPrice(),
              p.getOriginalPrice(),
              p.isOnSale(),
              p.getStock(),
              p.getImageUrl()
          ))
          .toList();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> one(@PathVariable Long id){
    return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @PostMapping public Product create(@RequestBody @Valid Product p){ p.setId(null); return repo.save(p); }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody @Valid Product p){
    return repo.findById(id).map(ex -> {
      p.setId(ex.getId()); return ResponseEntity.ok(repo.save(p));
    }).orElse(ResponseEntity.notFound().build());
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id){
    if (!repo.existsById(id)) return ResponseEntity.notFound().build();
    repo.deleteById(id); return ResponseEntity.noContent().build();
  }

}
