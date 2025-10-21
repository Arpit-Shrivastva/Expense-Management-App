package com.example.ExpensesManagement.Dto;

import java.time.YearMonth;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyExpenseSummaryDTO {
  private YearMonth month;
  private double totalAmount;
  private Map<String, Double> categoryTotals;

  private double totalBudget;
  private double totalIncome;
  private double remainingBudget;
}
