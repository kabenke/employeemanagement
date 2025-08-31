package com.studyjava.employeemanagement.controller;

import com.studyjava.employeemanagement.service.PasswordService;

import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me")
public class AccountController {

  private final PasswordService passwords;
  public AccountController(PasswordService passwords) { this.passwords = passwords; }

  public record ChangePwdRequest(@NotBlank String oldPassword, @NotBlank String newPassword) {}

  @PostMapping("/password")
  public ResponseEntity<?> changePassword(@AuthenticationPrincipal UserDetails me,
                                          @RequestBody ChangePwdRequest req) {
    String STRONG =
        "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.()\\[\\]{}~^_+\\-=|\\\\:;'\",<>/?`])[A-Za-z\\d@$!%*?&.()\\[\\]{}~^_+\\-=|\\\\:;'\",<>/?`]{8,}$";
    if (!req.newPassword().matches(STRONG)) {
      return ResponseEntity.badRequest().body(
          "Password must be at least 8 characters and include uppercase, lowercase, number, and special character");
    }
    passwords.changeOwnPassword(me.getUsername(), req.oldPassword(), req.newPassword());
    return ResponseEntity.ok("Password changed.");
  }
}

