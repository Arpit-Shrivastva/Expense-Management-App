package com.example.ExpensesManagement.Services;

import com.example.ExpensesManagement.Dto.ForgotPasswordRequest;
import com.example.ExpensesManagement.Dto.ResetPasswordRequest;
import com.example.ExpensesManagement.Exceptions.UserNotFoundException;
import com.example.ExpensesManagement.Models.User;
import com.example.ExpensesManagement.Repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public String forgotPassword(ForgotPasswordRequest request) {
    User user =
        userRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new UserNotFoundException("Email not found"));

    String token = UUID.randomUUID().toString();
    user.setResetToken(token);
    userRepository.save(user);

    // Simulate sending email
    return "Reset token (simulated): " + token;
  }

  @Override
  public String resetPassword(ResetPasswordRequest request) {
    User user =
        userRepository
            .findByResetToken(request.getToken())
            .orElseThrow(() -> new UserNotFoundException("Invalid token"));

    user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    user.setResetToken(null);
    userRepository.save(user);

    return "Password reset successful";
  }
}
