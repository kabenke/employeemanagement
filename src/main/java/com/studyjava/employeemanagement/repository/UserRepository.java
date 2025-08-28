package com.studyjava.employeemanagement.repository;

import com.studyjava.employeemanagement.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByUsernameIgnoreCase(String username);
  boolean existsByUsernameIgnoreCase(String username);
}
