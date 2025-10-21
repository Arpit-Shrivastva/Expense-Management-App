package com.example.ExpensesManagement.Repository;

import com.example.ExpensesManagement.Models.Budget;
import com.example.ExpensesManagement.Models.User;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
  List<Budget> findByUserUsernameAndMonth(String username, YearMonth month);

  Optional<Budget> findByUserUsernameAndMonthAndCategory(
      String username, YearMonth month, String category);

  List<Budget> findByUser_UsernameAndMonth(String username, YearMonth month);

  void deleteByUser(User user);
}
