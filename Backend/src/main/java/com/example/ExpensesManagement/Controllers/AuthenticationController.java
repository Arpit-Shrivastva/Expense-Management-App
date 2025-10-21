package com.example.ExpensesManagement.Controllers;

import com.example.ExpensesManagement.Dto.AuthRequest;
import com.example.ExpensesManagement.Dto.AuthResponse;
import com.example.ExpensesManagement.Dto.RegisterRequest;
import com.example.ExpensesManagement.Services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

  private final AuthenticationService authService;

  @PostMapping("/register")
  public String register(@RequestBody RegisterRequest request) {
    log.info("Register request received: {}", request);
    return authService.register(request);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
    log.info("Login request received for username: {}", request.getUsername());
    AuthResponse response = authService.login(request);
    return ResponseEntity.ok(response);
  }
}
