package com.example.ExpensesManagement.Controllers;

import com.example.ExpensesManagement.Dto.IncomeRequest;
import com.example.ExpensesManagement.Services.IncomeService;
import java.time.YearMonth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/income")
@RequiredArgsConstructor
@Slf4j
public class IncomeController {

  private final IncomeService incomeService;

  @PostMapping
  public ResponseEntity<String> addIncome(
      @RequestBody IncomeRequest request, @AuthenticationPrincipal UserDetails userDetails) {

    log.info("Adding income for user: {}", userDetails.getUsername());
    log.debug("Income details: amount={}, date={}", request.getAmount(), request.getDate());

    incomeService.addIncome(request, userDetails.getUsername());

    log.info("Income successfully added for user: {}", userDetails.getUsername());

    return ResponseEntity.ok("✅ Income added successfully");
  }

  @GetMapping()
  public ResponseEntity<Double> getTotalIncomeForMonth(
      @RequestParam YearMonth month, @AuthenticationPrincipal UserDetails userDetails) {

    log.info(
        "Received request to fetch total income for user: {} in month: {}",
        userDetails.getUsername(),
        month);
    Double total = incomeService.getTotalIncomeForMonth(userDetails.getUsername(), month);
    return ResponseEntity.ok(total);
  }
}
