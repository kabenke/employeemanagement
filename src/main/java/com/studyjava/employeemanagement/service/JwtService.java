package com.studyjava.employeemanagement.service;

import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
  private final String SECRET = "replace-with-strong-secret";
  public String generateToken(String username) {
    return Jwts.builder().setSubject(username)
      .setIssuedAt(new Date())
      .setExpiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
      .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
      .compact();
  }
  public String extractUsername(String token) {
    return Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build()
      .parseClaimsJws(token).getBody().getSubject();
  }
  public String generateToken(UserDetails user) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'generateToken'");
  }
}
