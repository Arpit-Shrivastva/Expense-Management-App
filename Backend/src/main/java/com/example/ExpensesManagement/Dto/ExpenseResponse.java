package com.example.ExpensesManagement.Dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpenseResponse {
  private Long id;
  private String description;
  private Double amount;
  private LocalDate date;
  private String category;
}
