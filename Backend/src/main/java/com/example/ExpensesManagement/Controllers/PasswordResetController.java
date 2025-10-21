package com.example.ExpensesManagement.Controllers;

import com.example.ExpensesManagement.Dto.ForgotPasswordRequest;
import com.example.ExpensesManagement.Dto.ResetPasswordRequest;
import com.example.ExpensesManagement.Services.PasswordResetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
@Slf4j
public class PasswordResetController {

  private final PasswordResetService passwordResetService;

  @PostMapping("/forgot")
  public String forgotPassword(@RequestBody ForgotPasswordRequest request) {
    log.info("Password reset request received for email: {}", request.getEmail());
    return passwordResetService.forgotPassword(request);
  }

  @PostMapping("/reset")
  public String resetPassword(@RequestBody ResetPasswordRequest request) {
    log.info("Password reset attempt for token: {}", request.getToken());
    return passwordResetService.resetPassword(request);
  }
}
