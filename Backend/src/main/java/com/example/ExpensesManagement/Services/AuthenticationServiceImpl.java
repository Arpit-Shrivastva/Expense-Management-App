package com.example.ExpensesManagement.Services;

import com.example.ExpensesManagement.Configuration.JwtUtil;
import com.example.ExpensesManagement.Dto.AuthRequest;
import com.example.ExpensesManagement.Dto.AuthResponse;
import com.example.ExpensesManagement.Dto.RegisterRequest;
import com.example.ExpensesManagement.Exceptions.UserNotFoundException;
import com.example.ExpensesManagement.Models.User;
import com.example.ExpensesManagement.Repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authManager;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  @Override
  @Transactional
  public String register(RegisterRequest request) {
    if (userRepository.findByUsername(request.getUsername()).isPresent()) {
      throw new IllegalArgumentException("Username already taken");
    }

    User user =
        User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .createdAt(LocalDateTime.now())
            .build();
    userRepository.save(user);
    return "User registered successfully";
  }

  @Override
  public AuthResponse login(AuthRequest request) {
    try {
      Authentication authentication =
          authManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  request.getUsername(), request.getPassword()));

      User user =
          userRepository
              .findByUsername(request.getUsername())
              .orElseThrow(() -> new UserNotFoundException("User not found"));

      String token = jwtUtil.generateToken(user.getUsername());

      return new AuthResponse(token, user.getUsername(), user.getRole(), user.getId());

    } catch (BadCredentialsException ex) {
      throw new RuntimeException("Invalid username or password", ex);
    }
  }
}
