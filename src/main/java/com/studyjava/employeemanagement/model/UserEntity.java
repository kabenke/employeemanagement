
package com.studyjava.employeemanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity @Table(name = "users") 
@Getter
@Setter
public class UserEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 100)
  private String username; // or email

  @Column(nullable = false, length = 95)
  private String password; 

  @Column(nullable = false)
  private boolean enabled = true;


  @Column(nullable = false, length = 200)
  private String roles = "ROLE_USER";

  public Set<String> roleSet() {
    return Stream.of(roles.split(",")).map(String::trim).filter(s -> !s.isBlank())
        .collect(Collectors.toSet());
  }
}
