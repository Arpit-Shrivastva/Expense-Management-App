import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ExpenseRequest } from 'src/app/Dto/expense-request';
import { ExpenseResponse } from 'src/app/Dto/expense-response';
import { ExpensesService } from 'src/app/Services/expenses.service';
import { NewExpenseComponent } from './new-expense/new-expense.component';

@Component({
  selector: 'app-expenses',
  templateUrl: './expenses.component.html',
  styleUrls: ['./expenses.component.css']
})
export class ExpensesComponent {

  expenses: ExpenseResponse[] = [];
  paginatedExpenses: ExpenseResponse[] = [];
  Math = Math;

  categories: string[] = [
    'Food', 'Transport', 'Utilities', 'Entertainment', 'Health',
    'Groceries', 'Shopping', 'Rent', 'Education', 'Other'
  ];

  pageSize = 5;
  currentPage = 0;

  selectedCategory: string = '';

  constructor(private expenseService: ExpensesService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.expenseService.getExpenses().subscribe(data => {
      this.expenses = data;
      this.updatePaginatedData();
    });
    this.getExpenses();
  }

  getExpenses(): void {
    this.expenseService.getExpenses().subscribe({
      next: (data) => {
        this.expenses = data;
      },
      error: (err) => {
        console.error('❌ Error fetching expenses:', err);
      }
    });
  }

  updatePaginatedData() {
    const start = this.currentPage * this.pageSize;
    const end = start + this.pageSize;
    this.paginatedExpenses = this.expenses.slice(start, end);
  }

  nextPage() {
    if ((this.currentPage + 1) * this.pageSize < this.expenses.length) {
      this.currentPage++;
      this.updatePaginatedData();
    }
  }

  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.updatePaginatedData();
    }
  }

  openAddExpenseDialog(): void {
    const dialogRef = this.dialog.open(NewExpenseComponent, {
      width: '400px'
    });

    dialogRef.afterClosed().subscribe((result: ExpenseRequest) => {
      if (result) {
        this.expenseService.addExpense(result).subscribe({
          next: (response: ExpenseResponse) => {
            console.log('✅ Expense added:', response);
            // Add the new item to the list
            this.expenses.unshift(response); // Add to beginning (or push to end)
            this.updatePaginatedData();      // Refresh the paginated table
          },
          error: (error) => {
            console.error('❌ Failed to add expense:', error);
          }
        });
      }
    });
  }


  openEditExpenseDialog(expense: ExpenseResponse): void {
    const dialogRef = this.dialog.open(NewExpenseComponent, {
      width: '400px',
      data: { ...expense } // Pass existing expense to pre-fill form
    });

    dialogRef.afterClosed().subscribe((updatedExpense: ExpenseRequest) => {
      if (updatedExpense) {
        this.expenseService.updateExpense(expense.id, updatedExpense).subscribe({
          next: (response: ExpenseResponse) => {
            console.log('✅ Expense updated:', response);
            this.getExpenses();
          },
          error: (error) => {
            console.error('❌ Failed to update expense:', error);
          }
        });
      }
    });
  }

  deleteExpense(id: number): void {
    if (confirm('Are you sure you want to delete this expense?')) {
      this.expenseService.deleteExpense(id).subscribe({
        next: () => {
          console.log('🗑️ Expense deleted');
          // Remove the deleted item locally
          this.expenses = this.expenses.filter(e => e.id !== id);
          this.updatePaginatedData(); // Update paginated view
        },
        error: (error) => {
          console.error('❌ Failed to delete expense:', error);
        }
      });
    }
  }


  startDate: Date | null = null;
  endDate: Date | null = null;

  filterByDateRange(): void {
    if (this.startDate && this.endDate) {
      const formattedStart = this.formatDate(this.startDate);
      const formattedEnd = this.formatDate(this.endDate);

      this.expenseService.getExpensesByDateRange(formattedStart, formattedEnd).subscribe({
        next: (data) => {
          this.expenses = data;
          this.currentPage = 0;
          this.updatePaginatedData();
        },
        error: (err) => {
          console.error('❌ Error filtering by date:', err);
        }
      });
    }
  }

  /** Formats date as 'YYYY-MM-DD' (ISO format without time) */
  formatDate(date: Date): string {
    return date.toISOString().split('T')[0];
  }




  filterByCategory(): void {
    if (this.selectedCategory) {
      this.expenseService.getExpensesByCategory(this.selectedCategory).subscribe({
        next: (data) => {
          this.expenses = data;
          this.currentPage = 0;
          this.updatePaginatedData();
        },
        error: (err) => console.error('❌ Error filtering by category:', err)
      });
    }
  }
}
