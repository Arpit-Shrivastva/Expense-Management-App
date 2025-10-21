package com.example.ExpensesManagement.Services;

import com.example.ExpensesManagement.Dto.BudgetComparisonResponse;
import com.example.ExpensesManagement.Dto.BudgetRequest;
import com.example.ExpensesManagement.Dto.BudgetResponse;
import com.example.ExpensesManagement.Models.Budget;
import com.example.ExpensesManagement.Models.Expense;
import com.example.ExpensesManagement.Models.User;
import com.example.ExpensesManagement.Repository.BudgetRepository;
import com.example.ExpensesManagement.Repository.ExpenseRepository;
import com.example.ExpensesManagement.Repository.UserRepository;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

  private final BudgetRepository budgetRepository;
  private final UserRepository userRepository;
  private final ExpenseRepository expenseRepository;

  @Override
  public BudgetResponse setBudget(String username, BudgetRequest request) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    Budget budget =
        budgetRepository
            .findByUserUsernameAndMonthAndCategory(
                username, request.getMonth(), request.getCategory())
            .orElse(new Budget());

    budget.setCategory(request.getCategory());
    budget.setAmount(request.getAmount());
    budget.setMonth(request.getMonth());
    budget.setUser(user);

    Budget saved = budgetRepository.save(budget);

    return new BudgetResponse(
        saved.getId(), saved.getCategory(), saved.getAmount(), saved.getMonth());
  }

  @Override
  public List<BudgetResponse> getBudgets(String username, YearMonth month) {
    return budgetRepository.findByUserUsernameAndMonth(username, month).stream()
        .map(b -> new BudgetResponse(b.getId(), b.getCategory(), b.getAmount(), b.getMonth()))
        .collect(Collectors.toList());
  }

  @Override
  public List<BudgetComparisonResponse> compareBudgetToSpending(String username, YearMonth month) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    // Fetch budgets for the month
    List<Budget> budgets = budgetRepository.findByUserUsernameAndMonth(username, month);

    // Define start and end of month
    LocalDate startDate = month.atDay(1);
    LocalDate endDate = month.atEndOfMonth();

    // Fetch expenses in that date range
    List<Expense> expenses =
        user.getId() != null
            ? expenseRepository.findByUserAndDateBetween(user, startDate, endDate)
            : Collections.emptyList();

    Map<String, Double> spentPerCategory =
        expenses.stream()
            .collect(
                Collectors.groupingBy(
                    e -> e.getCategory().trim().toLowerCase(),
                    Collectors.summingDouble(Expense::getAmount)));

    List<BudgetComparisonResponse> responseList = new ArrayList<>();

    for (Budget budget : budgets) {
      String category = budget.getCategory().trim().toLowerCase();
      Double spent = spentPerCategory.getOrDefault(category, 0.0);
      Double budgeted = budget.getAmount();
      Double remaining = budgeted - spent;

      responseList.add(
          new BudgetComparisonResponse(
              category, month, budgeted, spent, remaining, spent > budgeted));
    }

    return responseList;
  }

  @Override
  public Double getTotalBudgetForMonth(String username, YearMonth month) {
    return budgetRepository.findByUser_UsernameAndMonth(username, month).stream()
        .mapToDouble(Budget::getAmount)
        .sum();
  }
}
