// src/main/java/com/studyjava/employeemanagement/auth/RegisterController.java
package com.studyjava.employeemanagement.auth;

import com.studyjava.employeemanagement.model.UserEntity;
import com.studyjava.employeemanagement.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class RegisterController {

  private final UserRepository repo;
  private final PasswordEncoder encoder;

  public RegisterController(UserRepository repo, PasswordEncoder encoder) {
    this.repo = repo;
    this.encoder = encoder;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
    // Strong password: ≥8 chars, at least 1 upper, 1 lower, 1 digit, 1 special
    String STRONG =
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.()\\[\\]{}~^_+\\-=|\\\\:;'\",<>/?`])[A-Za-z\\d@$!%*?&.()\\[\\]{}~^_+\\-=|\\\\:;'\",<>/?`]{8,}$";

    if (!req.password().matches(STRONG)) {
      return ResponseEntity.badRequest().body(
          "Password must be at least 8 characters and include uppercase, lowercase, number, and special character"
      );
    }

    if (repo.existsByUsernameIgnoreCase(req.username())) {
      return ResponseEntity.badRequest().body("Username already exists");
    }

    UserEntity user = new UserEntity();
    user.setUsername(req.username().trim());
    user.setPassword(encoder.encode(req.password())); // hash before save
    user.setRoles("ROLE_USER");
    user.setEnabled(true);

    repo.save(user);

    return ResponseEntity.ok("User registered successfully");
  }

  public record RegisterRequest(String username, String password) {}
}
