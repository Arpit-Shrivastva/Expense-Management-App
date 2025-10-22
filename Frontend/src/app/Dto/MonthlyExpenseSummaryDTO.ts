export interface MonthlyExpenseSummaryDTO {
  month: string; 
  totalAmount: number;
  categoryTotals: { [category: string]: number };
  totalBudget: number;
  totalIncome: number;
  remainingBudget: number;
}
