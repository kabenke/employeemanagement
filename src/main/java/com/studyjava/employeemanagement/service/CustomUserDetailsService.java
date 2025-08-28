package com.studyjava.employeemanagement.service;



import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.studyjava.employeemanagement.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository repo;
  public CustomUserDetailsService(UserRepository repo) { this.repo = repo; }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var u = repo.findByUsernameIgnoreCase(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return org.springframework.security.core.userdetails.User
        .withUsername(u.getUsername())
        .password(u.getPassword())   // already BCrypt-encoded in DB
        .authorities(u.roleSet().toArray(String[]::new))
        .disabled(!u.isEnabled())
        .build();
  }
}
