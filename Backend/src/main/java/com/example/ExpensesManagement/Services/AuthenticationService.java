package com.example.ExpensesManagement.Services;

import com.example.ExpensesManagement.Dto.AuthRequest;
import com.example.ExpensesManagement.Dto.AuthResponse;
import com.example.ExpensesManagement.Dto.RegisterRequest;

public interface AuthenticationService {

  String register(RegisterRequest request);

  AuthResponse login(AuthRequest request);
}
