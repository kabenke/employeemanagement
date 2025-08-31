// com.studyjava.employeemanagement.repository.PasswordResetTokenRepository.java
package com.studyjava.employeemanagement.repository;

import com.studyjava.employeemanagement.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
  Optional<PasswordResetToken> findByToken(String token);
}
