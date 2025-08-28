
package com.studyjava.employeemanagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.studyjava.employeemanagement.model.UserEntity;
import com.studyjava.employeemanagement.repository.UserRepository;

@Configuration
public class Bootstrap {
  @Bean
  CommandLineRunner initUsers(UserRepository repo, PasswordEncoder encoder) {
    return args -> {
      if (!repo.existsByUsernameIgnoreCase("admin")) {
        var admin = new UserEntity();
        admin.setUsername("admin");
        admin.setPassword(encoder.encode("admin123"));
        admin.setRoles("ROLE_ADMIN,ROLE_USER");
        repo.save(admin);
      }
    };
  }
}
