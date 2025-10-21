package com.example.ExpensesManagement.Models;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Income {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Double amount;
  private LocalDate date;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @PrePersist
  protected void onCreate() {
    date = LocalDate.now();
  }
}
