package com.example.ExpensesManagement.Models;

import jakarta.persistence.*;
import java.time.YearMonth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Budget {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String category;

  private Double amount;

  private YearMonth month;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
