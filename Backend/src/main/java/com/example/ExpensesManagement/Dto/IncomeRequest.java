package com.example.ExpensesManagement.Dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class IncomeRequest {

  private Double amount;
  private LocalDate date;
}
