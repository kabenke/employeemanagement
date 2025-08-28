package com.studyjava.employeemanagement.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.studyjava.employeemanagement.model.UserEntity;
import com.studyjava.employeemanagement.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class RegisterController {
  private final UserRepository repo;
  private final PasswordEncoder encoder;

  public RegisterController(UserRepository repo, PasswordEncoder encoder) {
    this.repo = repo; this.encoder = encoder;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
    if (repo.existsByUsernameIgnoreCase(req.username())) {
      return ResponseEntity.badRequest().body("username already exists");
    }
    var u = new UserEntity();
    u.setUsername(req.username().trim());
    u.setPassword(encoder.encode(req.password())); // <-- BCrypt here
    u.setRoles(req.admin() ? "ROLE_ADMIN,ROLE_USER" : "ROLE_USER");
    repo.save(u);
    return ResponseEntity.ok().build();
  }

  public record RegisterRequest(String username, String password, boolean admin) {}
}
