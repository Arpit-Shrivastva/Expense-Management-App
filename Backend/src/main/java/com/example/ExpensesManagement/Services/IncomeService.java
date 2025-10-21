package com.example.ExpensesManagement.Services;

import com.example.ExpensesManagement.Dto.IncomeRequest;
import java.time.YearMonth;

public interface IncomeService {
  Double getTotalIncomeForMonth(String username, YearMonth month);

  void addIncome(IncomeRequest request, String username);
}
