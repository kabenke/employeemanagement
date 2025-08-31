package com.studyjava.employeemanagement.service;



import com.studyjava.employeemanagement.model.PasswordResetToken;
import com.studyjava.employeemanagement.model.UserEntity;
import com.studyjava.employeemanagement.repository.PasswordResetTokenRepository;
import com.studyjava.employeemanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Service
public class PasswordService {
  private final UserRepository users;
  private final PasswordResetTokenRepository tokens;
  private final PasswordEncoder encoder;
  private final MailService mail;

  @Value("${app.frontend.reset-url}")
  private String resetUrl;

  @Value("${app.reset.ttl-minutes:30}")
  private long ttlMinutes;

  private static final SecureRandom RNG = new SecureRandom();

  public PasswordService(UserRepository users, PasswordResetTokenRepository tokens,
                         PasswordEncoder encoder, MailService mail) {
    this.users = users; this.tokens = tokens; this.encoder = encoder; this.mail = mail;
  }

  public void startReset(String usernameOrEmail) {
    UserEntity user = users.findByUsernameIgnoreCase(usernameOrEmail)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    // generate random url-safe token
    byte[] bytes = new byte[32];
    RNG.nextBytes(bytes);
    String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

    var t = new PasswordResetToken();
    t.setToken(token);
    t.setUsername(user.getUsername());
    t.setExpiresAt(Instant.now().plus(ttlMinutes, ChronoUnit.MINUTES));
    t.setUsed(false);
    tokens.save(t);

    String link = resetUrl + "?token=" + token;
    String body = """
        Hello %s,
        
        Someone (hopefully you) requested a password reset.
        Use this link within %d minutes:
        %s
        
        If you didn't request this, you can ignore this email.
        """.formatted(user.getUsername(), ttlMinutes, link);

    // You might have an email column; replace with user.getEmail() if available
    // For now, send to username if it's an email; otherwise configure a real email field.
    mail.send(user.getUsername(), "Password Reset", body);
  }

  public void applyReset(String token, String newPassword) {
    var t = tokens.findByToken(token)
        .orElseThrow(() -> new IllegalArgumentException("Invalid token"));
    if (t.isUsed() || t.getExpiresAt().isBefore(Instant.now())) {
      throw new IllegalArgumentException("Token expired or already used");
    }
    var user = users.findByUsernameIgnoreCase(t.getUsername())
        .orElseThrow(() -> new IllegalStateException("User missing"));
    user.setPassword(encoder.encode(newPassword));
    users.save(user);

    t.setUsed(true);
    tokens.save(t);

    // Optional: email confirmation
    String body = """
        Hi %s,
        
        Your password was changed just now. If this wasn't you, contact support immediately.
        """.formatted(user.getUsername());
    mail.send(user.getUsername(), "Your password was changed", body);
  }

  public void changeOwnPassword(String username, String oldPassword, String newPassword) {
    var user = users.findByUsernameIgnoreCase(username)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
    if (!encoder.matches(oldPassword, user.getPassword())) {
      throw new IllegalArgumentException("Old password is incorrect");
    }
    user.setPassword(encoder.encode(newPassword));
    users.save(user);

    String body = """
        Hi %s,
        
        Your password was changed from your account settings.
        If you didn't do this, contact support immediately.
        """.formatted(user.getUsername());
    mail.send(user.getUsername(), "Your password was changed", body);
  }
}

