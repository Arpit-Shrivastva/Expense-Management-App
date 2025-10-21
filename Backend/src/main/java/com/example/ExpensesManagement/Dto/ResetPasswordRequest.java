package com.example.ExpensesManagement.Dto;

import lombok.Data;

@Data
public class ResetPasswordRequest {
  private String token;
  private String newPassword;
}
