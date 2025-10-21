package com.example.ExpensesManagement.Dto;

import java.time.YearMonth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetComparisonResponse {

  private String category;
  private YearMonth month;
  private Double budgetedAmount;
  private Double spentAmount;
  private Double remainingAmount;
  private boolean overBudget;
}
