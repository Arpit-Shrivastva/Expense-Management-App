package com.example.ExpensesManagement.Services;

import com.example.ExpensesManagement.Dto.UserUpdateRequest;
import com.example.ExpensesManagement.Models.User;
import com.example.ExpensesManagement.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User updateUser(Long id, UserUpdateRequest request) {
    User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    if (request.getUsername() != null) user.setUsername(request.getUsername());
    if (request.getEmail() != null) user.setEmail(request.getEmail());
    if (request.getPassword() != null && !request.getPassword().isBlank()) {
      user.setPassword(passwordEncoder.encode(request.getPassword()));
    }
    if (request.getRole() != null) user.setRole(request.getRole());

    return userRepository.save(user);
  }

  @Override
  public User getUserById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
  }
}
