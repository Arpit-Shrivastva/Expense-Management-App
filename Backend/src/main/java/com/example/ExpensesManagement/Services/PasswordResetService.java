package com.example.ExpensesManagement.Services;

import com.example.ExpensesManagement.Dto.ForgotPasswordRequest;
import com.example.ExpensesManagement.Dto.ResetPasswordRequest;

public interface PasswordResetService {
  String forgotPassword(ForgotPasswordRequest request);

  String resetPassword(ResetPasswordRequest request);
}
