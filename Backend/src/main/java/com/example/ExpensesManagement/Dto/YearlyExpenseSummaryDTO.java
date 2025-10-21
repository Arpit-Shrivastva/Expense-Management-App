package com.example.ExpensesManagement.Dto;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class YearlyExpenseSummaryDTO {
  private int year;
  private double totalAmount;
  private Map<String, Double> categoryTotals;
}
