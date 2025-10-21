package com.example.ExpensesManagement.Services;

import com.example.ExpensesManagement.Dto.BudgetComparisonResponse;
import com.example.ExpensesManagement.Dto.BudgetRequest;
import com.example.ExpensesManagement.Dto.BudgetResponse;
import java.time.YearMonth;
import java.util.List;

public interface BudgetService {
  BudgetResponse setBudget(String username, BudgetRequest request);

  List<BudgetResponse> getBudgets(String username, YearMonth month);

  List<BudgetComparisonResponse> compareBudgetToSpending(String username, YearMonth month);

  Double getTotalBudgetForMonth(String username, YearMonth month);
}
