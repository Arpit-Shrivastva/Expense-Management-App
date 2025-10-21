package com.example.ExpensesManagement.Services;

import com.example.ExpensesManagement.Exceptions.UserNotFoundException;
import com.example.ExpensesManagement.Models.User;
import com.example.ExpensesManagement.Repository.BudgetRepository;
import com.example.ExpensesManagement.Repository.ExpenseRepository;
import com.example.ExpensesManagement.Repository.IncomeRepository;
import com.example.ExpensesManagement.Repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
  private final UserRepository userRepository;
  private final ExpenseRepository expenseRepository;
  private final IncomeRepository incomeRepository;
  private final BudgetRepository budgetRepository;

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  @Transactional
  public void deleteUserByUsername(String username) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
    incomeRepository.deleteByUser(user);
    expenseRepository.deleteByUser(user);
    budgetRepository.deleteByUser(user);
    userRepository.delete(user);
  }
}
