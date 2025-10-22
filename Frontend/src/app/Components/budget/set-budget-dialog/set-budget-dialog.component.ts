import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDatepicker } from '@angular/material/datepicker';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { BudgetRequest } from 'src/app/Dto/BudgetRequest';

@Component({
  selector: 'app-set-budget-dialog',
  templateUrl: './set-budget-dialog.component.html',
  styleUrls: ['./set-budget-dialog.component.css']
})
export class SetBudgetDialogComponent {

  budgetForm: FormGroup;

  categories: string[] = [
    'Food', 'Transport', 'Utilities', 'Entertainment',
    'Health', 'Groceries', 'Shopping', 'Rent', 'Education', 'Other'
  ];

  chosenYear: Date | null = null;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<SetBudgetDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.budgetForm = this.fb.group({
      category: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(1)]],
      month: ['', Validators.required]  // Will be a Date object (1st of month)
    });
  }

  onSubmit(): void {
    if (this.budgetForm.valid) {
      const raw = this.budgetForm.value;

      // Format the month to YearMonth format (e.g. "2025-08")
      const budget: BudgetRequest = {
        ...raw,
        month: this.formatYearMonth(raw.month)
      };

      this.dialogRef.close(budget);
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  // Called when user selects a month
  chooseMonth(date: Date, datepicker: MatDatepicker<Date>): void {
    this.budgetForm.get('month')?.setValue(date);
    datepicker.close();
  }

  formatYearMonth(date: Date): string {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    return `${year}-${month}`;
  }

}
