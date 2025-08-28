// src/main/java/com/studyjava/employeemanagement/auth/AuthController.java
package com.studyjava.employeemanagement.auth;

import com.studyjava.employeemanagement.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthenticationManager authManager;
  private final JwtService jwt;

  public AuthController(AuthenticationManager authManager, JwtService jwt) {
    this.authManager = authManager; this.jwt = jwt;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest req) {
    Authentication a = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(req.username(), req.password()));
    UserDetails user = (UserDetails) a.getPrincipal();
    String token = jwt.generateToken(user);
    return ResponseEntity.ok(Map.of("token", token));
  }

  public record LoginRequest(String username, String password) {}
}
