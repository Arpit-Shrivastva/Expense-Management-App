package com.example.ExpensesManagement.Services;

import com.example.ExpensesManagement.Models.User;
import java.util.List;

public interface AdminService {

  List<User> getAllUsers();

  void deleteUserByUsername(String username);
}
