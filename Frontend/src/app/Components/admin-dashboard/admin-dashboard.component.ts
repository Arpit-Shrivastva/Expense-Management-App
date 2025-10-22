import { Component, OnInit } from '@angular/core';
import { ExpenseResponse } from 'src/app/Dto/expense-response';
import { MonthlyExpenseSummaryDTO } from 'src/app/Dto/MonthlyExpenseSummaryDTO';
import { AdminService } from 'src/app/Services/admin.service';
import { ExpensesService } from 'src/app/Services/expenses.service';
import { IncomeService } from 'src/app/Services/income.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit{

  
  totalUsers: number = 0;
  totalIncome: number = 0;
  totalExpenses: number = 0;

  allExpenses: ExpenseResponse[] = [];
  monthlySummary: MonthlyExpenseSummaryDTO[] = [];

  constructor(
    private adminService: AdminService,
    private expensesService: ExpensesService,
    private incomeService: IncomeService
  ) {}

  ngOnInit(): void {
    this.loadUserCount();
    this.loadTotalIncome();
    this.loadAllExpenses();
    this.loadMonthlySummary();
  }

  loadUserCount(): void {
    this.adminService.getAllUsers().subscribe(users => {
      this.totalUsers = users.length;
    });
  }

  loadTotalIncome(): void {
    const year = new Date().getFullYear();
    const month = String(new Date().getMonth() + 1).padStart(2, '0');
    const currentMonth = `${year}-${month}`;
    this.incomeService.getTotalIncomeForMonth(currentMonth).subscribe(income => {
      this.totalIncome = income;
    });
  }

  loadAllExpenses(): void {
    this.expensesService.getExpenses().subscribe(expenses => {
      this.allExpenses = expenses;
      this.totalExpenses = expenses.reduce((sum, e) => sum + e.amount, 0);
    });
  }

  loadMonthlySummary(): void {
    const year = new Date().getFullYear();
    this.expensesService.getMonthlySummary(year).subscribe(summary => {
      this.monthlySummary = summary;
    });
  }

}
