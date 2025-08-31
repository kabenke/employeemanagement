// src/main/java/com/studyjava/employeemanagement/service/JwtService.java
package com.studyjava.employeemanagement.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtService {

  @Value("${security.jwt.secret}")       
  private String secretB64;

  @Value("${security.jwt.ttl-seconds:3600}") 
  private long ttlSeconds;

  @Value("${security.jwt.issuer:employeemanagement}")
  private String issuer;

  private Key key() {
    byte[] keyBytes = Decoders.BASE64.decode(secretB64);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private JwtParser parser() {
    return Jwts.parserBuilder()
        .setSigningKey(key())
        .setAllowedClockSkewSeconds(60) 
        .requireIssuer(issuer)          
        .build();
  }

  public String generateToken(UserDetails user) {
    Date now = new Date();
    Date exp = new Date(now.getTime() + ttlSeconds * 1000);

    List<String> roles = user.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    return Jwts.builder()
        .setSubject(user.getUsername())
        .setIssuer(issuer)
        .setIssuedAt(now)
        .setExpiration(exp)
        .claim("roles", roles)
        .signWith(key(), SignatureAlgorithm.HS256)
        .compact();
  }

  public String extractUsername(String token) {
    return parser().parseClaimsJws(token).getBody().getSubject();
  }

  public boolean isTokenValid(String token, UserDetails user) {
    try {
      Claims c = parser().parseClaimsJws(token).getBody();
      return user.getUsername().equals(c.getSubject()) && c.getExpiration().after(new Date());
    } catch (JwtException | IllegalArgumentException e) {
      return false; 
    }
  }
}
