package com.example.ExpensesManagement.Services;

import com.example.ExpensesManagement.Dto.ExpenseRequest;
import com.example.ExpensesManagement.Dto.ExpenseResponse;
import com.example.ExpensesManagement.Dto.MonthlyExpenseSummaryDTO;
import com.example.ExpensesManagement.Dto.YearlyExpenseSummaryDTO;
import com.example.ExpensesManagement.Models.Expense;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface ExpenseService {
  ExpenseResponse addExpense(ExpenseRequest request, String username);

  List<ExpenseResponse> getExpenses(String username);

  ExpenseResponse updateExpense(Long id, ExpenseRequest request, String username);

  void deleteExpense(Long id, String username);

  List<Expense> getExpensesByDateRange(LocalDate startDate, LocalDate endDate);

  List<Expense> getExpensesByCategory(String category);

  List<MonthlyExpenseSummaryDTO> getMonthlyExpenseSummary(String username, int year);

  YearlyExpenseSummaryDTO getYearlyExpenseSummary(String username, int year);

  Double getTotalExpensesForMonth(String username, YearMonth month);

  MonthlyExpenseSummaryDTO getMonthlySummary(String username, YearMonth month);
}
