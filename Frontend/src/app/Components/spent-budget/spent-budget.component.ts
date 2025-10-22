import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDatepicker } from '@angular/material/datepicker';
import { PageEvent } from '@angular/material/paginator';
import { format } from 'date-fns';
import { BudgetComparisonResponse } from 'src/app/Dto/BudgetComparisonResponse';
import { BudgetService } from 'src/app/Services/budget.service';

@Component({
  selector: 'app-spent-budget',
  templateUrl: './spent-budget.component.html',
  styleUrls: ['./spent-budget.component.css']
})
export class SpentBudgetComponent implements OnInit {

  selectedMonth = new FormControl(new Date());
  comparisonResults: BudgetComparisonResponse[] = [];
  paginatedResults: BudgetComparisonResponse[] = [];

  pageSize = 5;
  currentPage = 0;

  constructor(private budgetService: BudgetService) {}

  ngOnInit(): void {
    this.loadComparison();
  }

  onMonthSelected(date: Date, datepicker: any): void {
    this.selectedMonth.setValue(date);
    datepicker.close();
    this.loadComparison();
  }

  loadComparison(): void {
    if (this.selectedMonth.value) {
      const formattedMonth = format(this.selectedMonth.value, 'yyyy-MM');
      this.budgetService.compareBudget(formattedMonth).subscribe({
        next: (data) => {
          this.comparisonResults = data;
          this.currentPage = 0;
          this.updatePagination();
        },
        error: (err) => {
          console.error('❌ Failed to load comparison:', err);
        },
      });
    }
  }

  updatePagination(): void {
    const start = this.currentPage * this.pageSize;
    const end = start + this.pageSize;
    this.paginatedResults = this.comparisonResults.slice(start, end);
  }

  onPageChange(event: PageEvent): void {
    this.pageSize = event.pageSize;
    this.currentPage = event.pageIndex;
    this.updatePagination();
  }

  getProgress(item: BudgetComparisonResponse): number {
    if (item.budgetedAmount === 0) return 0;
    return Math.min((item.spentAmount / item.budgetedAmount) * 100, 100);
  }

  getProgressColor(item: BudgetComparisonResponse): 'primary' | 'warn' | 'accent' {
    if (item.status === 'OVER') return 'warn';
    if (item.status === 'EQUAL') return 'accent';
    return 'primary';
  }

}
