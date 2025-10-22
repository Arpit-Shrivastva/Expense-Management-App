import { MonthlyExpenseSummaryDTO } from "./MonthlyExpenseSummaryDTO";

export interface YearlyExpenseSummaryDTO {
  year: number;
  totalAmount: number;
  monthlyBreakdown: MonthlyExpenseSummaryDTO[];
}