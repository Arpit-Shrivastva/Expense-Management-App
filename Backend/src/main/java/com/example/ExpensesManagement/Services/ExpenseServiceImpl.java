package com.example.ExpensesManagement.Services;

import com.example.ExpensesManagement.Dto.ExpenseRequest;
import com.example.ExpensesManagement.Dto.ExpenseResponse;
import com.example.ExpensesManagement.Dto.MonthlyExpenseSummaryDTO;
import com.example.ExpensesManagement.Dto.YearlyExpenseSummaryDTO;
import com.example.ExpensesManagement.Exceptions.UserNotFoundException;
import com.example.ExpensesManagement.Models.Expense;
import com.example.ExpensesManagement.Models.User;
import com.example.ExpensesManagement.Repository.ExpenseRepository;
import com.example.ExpensesManagement.Repository.UserRepository;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

  private final ExpenseRepository expenseRepository;
  private final UserRepository userRepository;
  private final BudgetService budgetService;
  private final IncomeService incomeService;

  @Transactional
  @Override
  public ExpenseResponse addExpense(ExpenseRequest request, String username) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

    LocalDate expenseDate = request.getDate() != null ? request.getDate() : LocalDate.now();

    Expense expense =
        Expense.builder()
            .description(request.getDescription())
            .amount(request.getAmount())
            .date(expenseDate)
            .category(request.getCategory())
            .user(user)
            .build();

    Expense saved = expenseRepository.save(expense);
    return mapToResponse(saved);
  }

  @Override
  public List<ExpenseResponse> getExpenses(String username) {
    List<Expense> expenses = expenseRepository.findAllByUserUsername(username);
    return expenses.stream().map(this::mapToResponse).collect(Collectors.toList());
  }

  @Override
  public ExpenseResponse updateExpense(Long id, ExpenseRequest request, String username) {
    Expense expense =
        expenseRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Expense not found"));

    if (!expense.getUser().getUsername().equals(username)) {
      throw new IllegalArgumentException("Not authorized to update this expense");
    }

    expense.setDescription(request.getDescription());
    expense.setAmount(request.getAmount());
    expense.setCategory(request.getCategory());

    Expense updated = expenseRepository.save(expense);
    return mapToResponse(updated);
  }

  @Override
  public void deleteExpense(Long id, String username) {
    Expense expense =
        expenseRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Expense not found"));

    if (!expense.getUser().getUsername().equals(username)) {
      throw new IllegalArgumentException("Not authorized to delete this expense");
    }

    expenseRepository.delete(expense);
  }

  public List<Expense> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
    return expenseRepository.findByDateBetween(startDate, endDate);
  }

  public List<Expense> getExpensesByCategory(String category) {
    return expenseRepository.findByCategoryIgnoreCase(category);
  }

  @Override
  public MonthlyExpenseSummaryDTO getMonthlySummary(String username, YearMonth month) {
    List<Expense> expenses =
        expenseRepository.findByUser_UsernameAndDateBetween(
            username, month.atDay(1), month.atEndOfMonth());

    Map<String, Double> categoryTotals =
        expenses.stream()
            .collect(
                Collectors.groupingBy(
                    Expense::getCategory, Collectors.summingDouble(Expense::getAmount)));

    double totalExpenses = expenses.stream().mapToDouble(Expense::getAmount).sum();
    double totalBudget = budgetService.getTotalBudgetForMonth(username, month);
    double totalIncome = incomeService.getTotalIncomeForMonth(username, month); // optional

    return MonthlyExpenseSummaryDTO.builder()
        .month(month)
        .totalAmount(totalExpenses)
        .categoryTotals(categoryTotals)
        .totalBudget(totalBudget)
        .totalIncome(totalIncome)
        .remainingBudget(totalBudget - totalExpenses)
        .build();
  }

  @Override
  public YearlyExpenseSummaryDTO getYearlyExpenseSummary(String username, int year) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    List<Expense> expenses = expenseRepository.findAllByUserAndYear(user, year);

    double total = expenses.stream().mapToDouble(Expense::getAmount).sum();

    Map<String, Double> categoryTotals =
        expenses.stream()
            .collect(
                Collectors.groupingBy(
                    Expense::getCategory, Collectors.summingDouble(Expense::getAmount)));

    return new YearlyExpenseSummaryDTO(year, total, categoryTotals);
  }

  private ExpenseResponse mapToResponse(Expense expense) {
    return ExpenseResponse.builder()
        .id(expense.getId())
        .description(expense.getDescription())
        .amount(expense.getAmount())
        .date(expense.getDate())
        .category(expense.getCategory())
        .build();
  }

  @Override
  public Double getTotalExpensesForMonth(String username, YearMonth month) {
    LocalDate start = month.atDay(1);
    LocalDate end = month.atEndOfMonth();

    return expenseRepository.findByUser_UsernameAndDateBetween(username, start, end).stream()
        .mapToDouble(Expense::getAmount)
        .sum();
  }

  @Override
  public List<MonthlyExpenseSummaryDTO> getMonthlyExpenseSummary(String username, int year) {
    List<MonthlyExpenseSummaryDTO> summaries = new ArrayList<>();

    for (int month = 1; month <= 12; month++) {
      YearMonth yearMonth = YearMonth.of(year, month);
      MonthlyExpenseSummaryDTO summary = getMonthlySummary(username, yearMonth);
      summaries.add(summary);
    }

    return summaries;
  }
}
