package com.example.ExpensesManagement.Repository;

import com.example.ExpensesManagement.Models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

  Optional<User> findByResetToken(String token);

  boolean existsByUsername(String username);
}
