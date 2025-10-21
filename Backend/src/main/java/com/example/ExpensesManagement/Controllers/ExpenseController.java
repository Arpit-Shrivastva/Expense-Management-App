package com.example.ExpensesManagement.Controllers;

import com.example.ExpensesManagement.Dto.ExpenseRequest;
import com.example.ExpensesManagement.Dto.ExpenseResponse;
import com.example.ExpensesManagement.Dto.MonthlyExpenseSummaryDTO;
import com.example.ExpensesManagement.Dto.YearlyExpenseSummaryDTO;
import com.example.ExpensesManagement.Models.Expense;
import com.example.ExpensesManagement.Services.ExpenseService;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
@Slf4j
public class ExpenseController {
  private final ExpenseService expenseService;

  @PostMapping
  public ExpenseResponse addExpense(
      @RequestBody ExpenseRequest request, @AuthenticationPrincipal UserDetails userDetails) {
    log.info("Add expense request by user: {}", userDetails.getUsername());
    return expenseService.addExpense(request, userDetails.getUsername());
  }

  @GetMapping
  public List<ExpenseResponse> getExpenses(@AuthenticationPrincipal UserDetails userDetails) {
    log.info("Get expenses request by user: {}", userDetails.getUsername());

    return expenseService.getExpenses(userDetails.getUsername());
  }

  @PutMapping("/{id}")
  public ExpenseResponse updateExpense(
      @PathVariable Long id,
      @RequestBody ExpenseRequest request,
      @AuthenticationPrincipal UserDetails userDetails) {
    log.info("Update expense request. ID: {}, User: {}", id, userDetails.getUsername());
    return expenseService.updateExpense(id, request, userDetails.getUsername());
  }

  @DeleteMapping("/{id}")
  public void deleteExpense(
      @PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
    log.info("Delete expense request. ID: {}, User: {}", id, userDetails.getUsername());
    expenseService.deleteExpense(id, userDetails.getUsername());
  }

  @GetMapping("/date-range")
  public ResponseEntity<List<Expense>> getExpensesByDateRange(
      @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
      @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

    log.info("Fetching expenses from {} to {}", start, end);

    List<Expense> expenses = expenseService.getExpensesByDateRange(start, end);

    log.info("Found {} expenses in date range", expenses.size());

    return ResponseEntity.ok(expenses);
  }

  @GetMapping("/by-category")
  public ResponseEntity<List<Expense>> getExpensesByCategory(@RequestParam String category) {
    log.info("Fetching expenses for category: {}", category);

    List<Expense> expenses = expenseService.getExpensesByCategory(category);

    log.info("Found {} expenses under category '{}'", expenses.size(), category);
    return ResponseEntity.ok(expenses);
  }

  @GetMapping("/monthly")
  public ResponseEntity<List<MonthlyExpenseSummaryDTO>> getMonthlySummary(
      @AuthenticationPrincipal UserDetails userDetails, @RequestParam int year) {

    log.info("📊 Monthly summary request by user: {}, year: {}", userDetails.getUsername(), year);

    List<MonthlyExpenseSummaryDTO> summary =
        expenseService.getMonthlyExpenseSummary(userDetails.getUsername(), year);

    log.info("✅ Monthly summary returned. Entries: {}", summary.size());

    return ResponseEntity.ok(summary);
  }

  @GetMapping("/summary")
  public ResponseEntity<MonthlyExpenseSummaryDTO> getMonthlySummaryForMonth(
      @AuthenticationPrincipal UserDetails userDetails, @RequestParam YearMonth month) {
    String username = userDetails.getUsername();
    log.info(
        "📊 Request received for monthly expense summary - User: {}, Month: {}", username, month);

    MonthlyExpenseSummaryDTO summary =
        expenseService.getMonthlySummary(userDetails.getUsername(), month);
    log.info("✅ Monthly expense summary retrieved for User: {}, Month: {}", username, month);

    return ResponseEntity.ok(summary);
  }

  @GetMapping("/yearly")
  public ResponseEntity<YearlyExpenseSummaryDTO> getYearlySummary(
      @AuthenticationPrincipal UserDetails userDetails, @RequestParam int year) {

    log.info("📊 Yearly summary request by user: {}, year: {}", userDetails.getUsername(), year);

    YearlyExpenseSummaryDTO summary =
        expenseService.getYearlyExpenseSummary(userDetails.getUsername(), year);

    log.info("✅ Yearly summary returned. Total: {}", summary.getTotalAmount());

    return ResponseEntity.ok(summary);
  }
}
