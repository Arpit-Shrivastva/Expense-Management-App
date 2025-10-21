package com.example.ExpensesManagement.Dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ExpenseRequest {
  private String description;
  private Double amount;
  private LocalDate date;
  private String category;
}
