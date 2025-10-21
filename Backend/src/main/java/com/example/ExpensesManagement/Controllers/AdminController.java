package com.example.ExpensesManagement.Controllers;

import com.example.ExpensesManagement.Models.User;
import com.example.ExpensesManagement.Services.AdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

  private final AdminService adminService;

  @GetMapping("/users")
  public ResponseEntity<List<User>> getAllUsers() {
    log.info("🔍 Received request to fetch all users");
    List<User> users = adminService.getAllUsers();
    log.info("✅ Retrieved {} users", users.size());
    return ResponseEntity.ok(users);
  }

  @DeleteMapping("/user/{username}")
  public ResponseEntity<String> deleteUser(@PathVariable String username) {
    log.info("🗑 Received request to delete user with username: {}", username);
    try {
      adminService.deleteUserByUsername(username);
      log.info("✅ Successfully deleted user: {}", username);
      return ResponseEntity.ok("✅ User deleted: " + username);
    } catch (Exception e) {
      log.error("❌ Failed to delete user: {}", username, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("❌ Failed to delete user: " + username);
    }
  }
}
