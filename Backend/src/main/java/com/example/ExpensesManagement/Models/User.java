package com.example.ExpensesManagement.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;

  @Column(unique = true)
  private String email;

  private String password;

  private LocalDateTime createdAt;

  @Column(name = "reset_token")
  private String resetToken;

  private String role;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();

    // Set default role if not already assigned
    if (role == null || role.isBlank()) {
      role = "ROLE_USER";
    }
  }
}
