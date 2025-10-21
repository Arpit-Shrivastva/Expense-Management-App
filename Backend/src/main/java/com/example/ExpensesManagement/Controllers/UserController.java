package com.example.ExpensesManagement.Controllers;

import com.example.ExpensesManagement.Dto.UserUpdateRequest;
import com.example.ExpensesManagement.Models.User;
import com.example.ExpensesManagement.Services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
  private final UserService userService;

  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(
      @PathVariable Long id, @RequestBody UserUpdateRequest request) {
    log.info("🔄 Update request for user ID: {} with data: {}", id, request);
    User updatedUser = userService.updateUser(id, request);
    log.info("✅ Successfully updated user ID: {}", id);
    return ResponseEntity.ok(updatedUser);
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    log.info("📥 Fetching user with ID: {}", id);
    User user = userService.getUserById(id);
    log.info("✅ User retrieved: {}", user.getUsername());
    return ResponseEntity.ok(user);
  }
}
