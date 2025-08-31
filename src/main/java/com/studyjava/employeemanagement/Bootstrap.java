
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
        UserEntity admin = new UserEntity();
        admin.setUsername("kabenke_nana@yahoo.fr");
        admin.setPassword(encoder.encode("Jo60papd%")); 
        admin.setRoles("ROLE_ADMIN,ROLE_USER");
        admin.setEnabled(true);
        repo.save(admin);
        System.out.println("✅ Seeded dummy admin user: username=admin / password=admin123");
      }
    };
  }
}
