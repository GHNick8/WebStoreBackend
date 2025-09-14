package com.example.store.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.store.dto.AuthRequest;
import com.example.store.dto.AuthResponse;
import com.example.store.dto.RegisterRequest;
import com.example.store.entity.User;
import com.example.store.repository.UserRepository;
import com.example.store.security.JwtService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/auth")
public class AuthController {
  private final UserRepository users; private final PasswordEncoder encoder;
  private final AuthenticationManager authManager; private final JwtService jwt;

  public AuthController(UserRepository users, PasswordEncoder encoder, AuthenticationManager am, JwtService jwt) {
    this.users = users; this.encoder = encoder; this.authManager = am; this.jwt = jwt;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest req) {
    if (users.existsByEmail(req.email())) return ResponseEntity.status(HttpStatus.CONFLICT).body("Email in use");
    User u = new User(); u.setEmail(req.email()); u.setPassword(encoder.encode(req.password())); u.setRoles("ROLE_USER");
    users.save(u);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
      Authentication a = authManager.authenticate(
          new UsernamePasswordAuthenticationToken(req.email(), req.password())
      );

      String token = jwt.generate(req.email(), a.getAuthorities());
      String role = a.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .orElse("USER");

      return ResponseEntity.ok(new AuthResponse(token, role));
  }
    
}
