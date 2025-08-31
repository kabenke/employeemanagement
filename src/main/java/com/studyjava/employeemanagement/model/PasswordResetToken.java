package com.studyjava.employeemanagement.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity @Table(name = "password_reset_tokens") @Setter @Getter
public class PasswordResetToken {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false, unique=true)
  private String token;

  @Column(nullable=false)
  private String username; 

  @Column(nullable=false)
  private Instant expiresAt;

  @Column(nullable=false)
  private boolean used = false;

}
