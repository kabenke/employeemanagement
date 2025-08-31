package com.studyjava.employeemanagement.controller;

import com.studyjava.employeemanagement.service.PasswordService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class PasswordResetController {

  private final PasswordService passwords;

  public PasswordResetController(PasswordService passwords) { this.passwords = passwords; }

  public record ForgotRequest(@NotBlank String usernameOrEmail) {}
  public record ResetRequest(@NotBlank String token, @NotBlank String newPassword) {}

  @PostMapping("/password/forgot")
  public ResponseEntity<?> forgot(@RequestBody ForgotRequest req) {
    // respond 200 even if user not found to avoid user enumeration; log internally if you prefer
    try { passwords.startReset(req.usernameOrEmail()); } catch (Exception ignored) {}
    return ResponseEntity.ok("If the account exists, an email has been sent.");
  }

  @PostMapping("/password/reset")
  public ResponseEntity<?> reset(@RequestBody ResetRequest req) {
    // Enforce strong password here too (same regex you used on register)
    String STRONG =
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.()\\[\\]{}~^_+\\-=|\\\\:;'\",<>/?`])[A-Za-z\\d@$!%*?&.()\\[\\]{}~^_+\\-=|\\\\:;'\",<>/?`]{8,}$";
    if (!req.newPassword().matches(STRONG)) {
      return ResponseEntity.badRequest().body(
          "Password must be at least 8 characters and include uppercase, lowercase, number, and special character");
    }
    passwords.applyReset(req.token(), req.newPassword());
    return ResponseEntity.ok("Password has been reset.");
  }
}
