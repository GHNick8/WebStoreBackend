package com.example.store.security;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
  @Value("${app.jwt.secret}") private String secret;
  @Value("${app.jwt.expiration-ms}") private long expirationMs;

  public String generate(String subject, Collection<? extends GrantedAuthority> roles) {
    Date now = new Date();
    return Jwts.builder()
      .setSubject(subject)
      .claim("roles", roles.stream().map(GrantedAuthority::getAuthority).toList())
      .setIssuedAt(now)
      .setExpiration(new Date(now.getTime() + expirationMs))
      .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
      .compact();
  }

  public String extractUsername(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
      .build().parseClaimsJws(token).getBody().getSubject();
  }

}
