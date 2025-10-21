package com.example.ExpensesManagement.Dto;

import java.time.YearMonth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetResponse {

  private Long id;
  private String category;
  private Double amount;
  private YearMonth month;
}
