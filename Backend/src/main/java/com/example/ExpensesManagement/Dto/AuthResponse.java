package com.example.ExpensesManagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
  private String token;
  private String username;
  private String role;
  private Long id;
}
