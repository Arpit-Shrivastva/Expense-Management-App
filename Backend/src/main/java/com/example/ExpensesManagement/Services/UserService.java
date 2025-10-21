package com.example.ExpensesManagement.Services;

import com.example.ExpensesManagement.Dto.UserUpdateRequest;
import com.example.ExpensesManagement.Models.User;

public interface UserService {
  User updateUser(Long id, UserUpdateRequest request);

  User getUserById(Long id);
}
