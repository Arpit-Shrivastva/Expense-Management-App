import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ExpenseRequest } from 'src/app/Dto/expense-request';

@Component({
  selector: 'app-new-expense',
  templateUrl: './new-expense.component.html',
  styleUrls: ['./new-expense.component.css']
})
export class NewExpenseComponent {

  expenseForm: FormGroup;

  categories: string[] = [
    'Food',
    'Transport',
    'Utilities',
    'Entertainment',
    'Health',
    'Groceries',
    'Shopping',
    'Rent',
    'Education',
    'Other'
  ];

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<NewExpenseComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.expenseForm = this.fb.group({
      date: [this.toISODateString(data?.date || new Date()), Validators.required],
      amount: [data?.amount || '', [Validators.min(1)]],
      description: [data?.description || '', Validators.required],
      category: [data?.category || '', Validators.required]
    });
  }

  toISODateString(date: Date): string {
    const localDate = new Date(date);
    const year = localDate.getFullYear();
    const month = ('0' + (localDate.getMonth() + 1)).slice(-2);
    const day = ('0' + localDate.getDate()).slice(-2);
    return `${year}-${month}-${day}`;
  }



  onSubmit(): void {
    if (this.expenseForm.valid) {
      const formValue = this.expenseForm.value;
      const expenseData: ExpenseRequest = {
        ...formValue,
        date: this.formatLocalDate(formValue.date)  // Converts Date to 'YYYY-MM-DD'
      };
      this.dialogRef.close(expenseData);
    }
  }


  formatLocalDate(date: any): string {
    const parsedDate = new Date(date);
    if (isNaN(parsedDate.getTime())) {
      console.error('Invalid date provided:', date);
      return '';
    }
    const year = parsedDate.getFullYear();
    const month = String(parsedDate.getMonth() + 1).padStart(2, '0');
    const day = String(parsedDate.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }



  onCancel(): void {
    this.dialogRef.close();
  }
}
