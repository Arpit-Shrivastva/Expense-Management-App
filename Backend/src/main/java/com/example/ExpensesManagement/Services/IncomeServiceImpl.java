package com.example.ExpensesManagement.Services;

import com.example.ExpensesManagement.Dto.IncomeRequest;
import com.example.ExpensesManagement.Models.Income;
import com.example.ExpensesManagement.Models.User;
import com.example.ExpensesManagement.Repository.IncomeRepository;
import com.example.ExpensesManagement.Repository.UserRepository;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

  private final IncomeRepository incomeRepository;
  private final UserRepository userRepository;

  @Override
  public void addIncome(IncomeRequest request, String username) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    LocalDate incomeDate = request.getDate();
    if (incomeDate == null) {
      incomeDate = LocalDate.now();
    }

    YearMonth incomeMonth = YearMonth.from(incomeDate);

    List<Income> existingIncomes =
        incomeRepository.findByUserAndDateBetween(
            user, incomeMonth.atDay(1), incomeMonth.atEndOfMonth());

    if (!existingIncomes.isEmpty()) {
      throw new IllegalStateException("Income already exists for this month.");
    }

    Income income = new Income();
    income.setAmount(request.getAmount());
    income.setDate(incomeDate);
    income.setUser(user);

    incomeRepository.save(income);
  }

  @Override
  public Double getTotalIncomeForMonth(String username, YearMonth month) {
    LocalDate start = month.atDay(1);
    LocalDate end = month.atEndOfMonth();

    return incomeRepository.findByUser_UsernameAndDateBetween(username, start, end).stream()
        .mapToDouble(Income::getAmount)
        .sum();
  }
}
