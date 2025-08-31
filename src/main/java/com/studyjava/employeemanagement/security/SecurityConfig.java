package com.studyjava.employeemanagement.security;

import com.studyjava.employeemanagement.component.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration @EnableWebSecurity
public class SecurityConfig {

  @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

  @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager(); 
  }

  @Bean
  public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
    var cfg = new org.springframework.web.cors.CorsConfiguration();
 
    cfg.setAllowedOrigins(java.util.List.of(
        "http://localhost:3000"  
    ));
    cfg.setAllowedMethods(java.util.List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
    cfg.setAllowedHeaders(java.util.List.of("Authorization","Content-Type"));
    cfg.setAllowCredentials(false); 
    cfg.setMaxAge(3600L);

    var source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", cfg);
    return source;
  }


  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .cors(cors -> {})
      .authorizeHttpRequests(auth -> auth
          .requestMatchers("/auth/**", "/actuator/**", "/v3/api-docs/**", "/swagger-ui/**, \"/auth/password/forgot\",\n" + //
                        "  \"/auth/password/reset\",\n" + //
                        "  \"/auth/register\",\n" + //
                        "  \"/auth/login\"").permitAll()
          .anyRequest().authenticated()
      )
      .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
