import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { format } from 'date-fns';
import { BudgetResponse } from 'src/app/Dto/BudgetResponse';
import { BudgetService } from 'src/app/Services/budget.service';
import { SetBudgetDialogComponent } from './set-budget-dialog/set-budget-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { BudgetRequest } from 'src/app/Dto/BudgetRequest';
import { MatDatepicker } from '@angular/material/datepicker';
import { BudgetComparisonResponse } from 'src/app/Dto/BudgetComparisonResponse';

@Component({
  selector: 'app-budget',
  templateUrl: './budget.component.html',
  styleUrls: ['./budget.component.css']
})
export class BudgetComponent implements OnInit {

 budgets: BudgetResponse[] = [];
  selectedMonth = new FormControl(new Date());
  Math = Math;

  // Pagination
  pageSize = 5;
  currentPage = 0;

  constructor(
    private budgetService: BudgetService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadBudgets();
  }

  loadBudgets(): void {
    const month = this.selectedMonth.value;
    if (month) {
      const formattedMonth = format(month, 'yyyy-MM');
      this.budgetService.getBudgets(formattedMonth).subscribe({
        next: (data) => this.budgets = data,
        error: (err) => console.error('❌ Failed to load budgets:', err)
      });
    }
  }

  onMonthSelected(event: Date, datepicker: MatDatepicker<Date>) {
    this.selectedMonth.setValue(event);
    datepicker.close();
    this.loadBudgets();
  }

  openSetBudgetDialog(): void {
    const dialogRef = this.dialog.open(SetBudgetDialogComponent, {
      width: '400px'
    });

    dialogRef.afterClosed().subscribe((budget: BudgetRequest) => {
      if (budget) {
        this.budgetService.setBudget(budget).subscribe({
          next: () => this.loadBudgets(),
          error: (err) => console.error('❌ Failed to set budget:', err)
        });
      }
    });
  }

  // Pagination helpers
  get paginatedBudgets(): BudgetResponse[] {
    const start = this.currentPage * this.pageSize;
    return this.budgets.slice(start, start + this.pageSize);
  }

  nextPage(): void {
    if ((this.currentPage + 1) * this.pageSize < this.budgets.length) {
      this.currentPage++;
    }
  }

  prevPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
    }
  }
}
