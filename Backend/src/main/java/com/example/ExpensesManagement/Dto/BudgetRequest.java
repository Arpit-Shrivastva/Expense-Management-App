package com.example.ExpensesManagement.Dto;

import java.time.YearMonth;
import lombok.Data;

@Data
public class BudgetRequest {
  private String category;
  private Double amount;
  private YearMonth month;
}
