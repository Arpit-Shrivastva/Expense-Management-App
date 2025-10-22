import { Component, OnInit } from '@angular/core';
import { ChartConfiguration, ChartOptions } from 'chart.js';
import { ExpenseResponse } from 'src/app/Dto/expense-response';
import { MonthlyExpenseSummaryDTO } from 'src/app/Dto/MonthlyExpenseSummaryDTO';
import { ExpensesService } from 'src/app/Services/expenses.service';
import { IncomeService } from 'src/app/Services/income.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {


  totalIncome: number = 0;
  totalSpent: number = 0;
  remaining: number = 0;
  currentMonth: string = '';

  latestExpenses: ExpenseResponse[] = [];
  categorySummary: { category: string, amount: number }[] = [];

  lineChartType: 'line' = 'line'; // Instead of ChartType = 'line'

  // ✅ Chart config for Chart.js v4+
  lineChartData: ChartConfiguration<'line'>['data'] = {
    labels: [],
    datasets: [{
      data: [],
      label: 'Monthly Spending',
      borderColor: '#3f51b5',
      backgroundColor: 'rgba(63, 81, 181, 0.3)',
      pointBackgroundColor: '#3f51b5',
      tension: 0.4,
      fill: true
    }]
  };

  lineChartOptions: ChartOptions<'line'> = {
    responsive: true,
    plugins: {
      legend: {
        display: true
      }
    }
  };

  // lineChartType: ChartType = 'line';


  constructor(
    private incomeService: IncomeService,
    private expensesService: ExpensesService,
  ) { }
  

  ngOnInit(): void {
  const now = new Date();
  this.currentMonth = `${now.getFullYear()}-${(now.getMonth() + 1).toString().padStart(2, '0')}`;

  this.loadSummary();
  this.loadChartData();
  this.loadRecentExpenses();

  // 🔁 Auto-refresh chart every 30 seconds (30000 ms)
  setInterval(() => {
    this.loadChartData();
  }, 30000);
}


  loadSummary(): void {
    this.incomeService.getTotalIncomeForMonth(this.currentMonth).subscribe(income => {
      this.totalIncome = income;

      this.expensesService.getMonthlySummaryForMonth(this.currentMonth).subscribe(summary => {
        this.totalSpent = summary.totalAmount;
        this.remaining = this.totalIncome - this.totalSpent;
      });
    });
  }

  loadChartData(): void {
  const year = new Date().getFullYear();

  this.expensesService.getMonthlySummary(year).subscribe((data: MonthlyExpenseSummaryDTO[]) => {
    const labels = data.map(d => `M${d.month}`);
    const amounts = data.map(d => d.totalAmount);

    // Replace the entire object to trigger chart refresh
    this.lineChartData = {
      labels: labels,
      datasets: [{
        data: amounts,
        label: 'Monthly Spending',
        borderColor: '#3f51b5',
        backgroundColor: 'rgba(63, 81, 181, 0.3)',
        pointBackgroundColor: '#3f51b5',
        tension: 0.4,
        fill: true
      }]
    };
  });
}

private getMonthName(month: number): string {
  const monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
                      "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
  return monthNames[month - 1];
}


  loadRecentExpenses(): void {
  this.expensesService.getExpenses().subscribe(expenses => {
    this.latestExpenses = expenses
      .sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime())
      .slice(0, 3); // ⬅️ Changed from 5 to 3

    const grouped: { [key: string]: number } = {};
    for (const e of expenses) {
      grouped[e.category] = (grouped[e.category] || 0) + e.amount;
    }

    this.categorySummary = Object.keys(grouped).map(category => ({
      category,
      amount: grouped[category]
    }));
  });
}

}
