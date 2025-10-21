package com.example.ExpensesManagement.Models;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Expense {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Double amount;

  private String category;

  private String description;

  private LocalDate date;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @PrePersist
  protected void onCreate() {
    if (this.date == null) {
      this.date = LocalDate.now();
    }
  }
}
