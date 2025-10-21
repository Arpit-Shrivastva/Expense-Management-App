package com.example.ExpensesManagement.Repository;

import com.example.ExpensesManagement.Models.Income;
import com.example.ExpensesManagement.Models.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
  List<Income> findByUser_UsernameAndDateBetween(String username, LocalDate start, LocalDate end);

  List<Income> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);

  void deleteByUser(User user);
}
