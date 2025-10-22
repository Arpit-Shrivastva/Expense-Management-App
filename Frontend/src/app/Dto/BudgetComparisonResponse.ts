export interface BudgetComparisonResponse {
  category: string;
  budgetedAmount: number;
  spentAmount: number;
  remainingAmount: number;
  status: 'UNDER' | 'OVER' | 'EQUAL';
}