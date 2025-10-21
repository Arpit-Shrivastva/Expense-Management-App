package com.example.ExpensesManagement.Repository;

import com.example.ExpensesManagement.Models.Expense;
import com.example.ExpensesManagement.Models.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
  List<Expense> findAllByUserUsername(String username);

  void deleteByUser(User user);

  List<Expense> findByDateBetween(LocalDate start, LocalDate end);

  List<Expense> findByCategoryIgnoreCase(String category);

  @Query("SELECT e FROM Expense e WHERE e.user = :user AND YEAR(e.date) = :year")
  List<Expense> findAllByUserAndYear(@Param("user") User user, @Param("year") int year);

  List<Expense> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);

  List<Expense> findByUser_UsernameAndDateBetween(String username, LocalDate start, LocalDate end);
}
