package com.example.ExpensesManagement.Controllers;

import com.example.ExpensesManagement.Dto.BudgetComparisonResponse;
import com.example.ExpensesManagement.Dto.BudgetRequest;
import com.example.ExpensesManagement.Dto.BudgetResponse;
import com.example.ExpensesManagement.Services.BudgetService;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/budgets")
@RequiredArgsConstructor
@Slf4j
public class BudgetController {

  private final BudgetService budgetService;

  @PostMapping
  public ResponseEntity<BudgetResponse> setBudget(
      @RequestBody BudgetRequest request, @AuthenticationPrincipal UserDetails userDetails) {

    log.info(
        "📌 Set budget request by user: {}, Category: {}, Month: {}",
        userDetails.getUsername(),
        request.getCategory(),
        request.getMonth());

    BudgetResponse response = budgetService.setBudget(userDetails.getUsername(), request);

    log.info("✅ Budget set successfully: {}", response);

    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<List<BudgetResponse>> getBudgets(
      @RequestParam YearMonth month, @AuthenticationPrincipal UserDetails userDetails) {

    log.info("📤 Fetch budgets for user: {}, Month: {}", userDetails.getUsername(), month);

    List<BudgetResponse> responses = budgetService.getBudgets(userDetails.getUsername(), month);

    log.info("🔍 Found {} budgets for month {}", responses.size(), month);

    return ResponseEntity.ok(responses);
  }

  @GetMapping("/comparison")
  public ResponseEntity<List<BudgetComparisonResponse>> compareBudget(
      @RequestParam YearMonth month, @AuthenticationPrincipal UserDetails userDetails) {

    log.info(
        "📊 Budget comparison requested for user: {}, month: {}", userDetails.getUsername(), month);

    List<BudgetComparisonResponse> comparisonList =
        budgetService.compareBudgetToSpending(userDetails.getUsername(), month);

    log.info("✅ Budget comparison result size: {}", comparisonList.size());

    return ResponseEntity.ok(comparisonList);
  }
}
