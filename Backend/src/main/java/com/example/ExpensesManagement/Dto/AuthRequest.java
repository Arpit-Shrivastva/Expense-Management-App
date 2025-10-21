package com.example.ExpensesManagement.Dto;

import lombok.Data;

@Data
public class AuthRequest {
  private String username;
  private String password;
}
