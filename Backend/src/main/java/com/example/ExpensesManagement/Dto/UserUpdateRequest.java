package com.example.ExpensesManagement.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
  private Long id;
  private String username;
  private String email;
  private String password;
  private String role;
}
