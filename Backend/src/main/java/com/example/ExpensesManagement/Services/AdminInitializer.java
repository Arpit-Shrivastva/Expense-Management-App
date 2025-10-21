package com.example.ExpensesManagement.Services;

import com.example.ExpensesManagement.Configuration.AdminConfig;
import com.example.ExpensesManagement.Models.User;
import com.example.ExpensesManagement.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

  @Bean
  public CommandLineRunner initAdminUser(
      UserRepository userRepository, AdminConfig adminConfig, PasswordEncoder passwordEncoder) {
    return args -> {
      if (!userRepository.existsByUsername(adminConfig.getUsername())) {
        User admin = new User();
        admin.setUsername(adminConfig.getUsername());
        admin.setPassword(passwordEncoder.encode(adminConfig.getPassword()));
        admin.setEmail(adminConfig.getEmail());
        admin.setRole("ROLE_ADMIN");

        userRepository.save(admin);
        System.out.println("✅ Default admin user created.");
      } else {
        System.out.println("ℹ️ Admin user already exists.");
      }
    };
  }
}
