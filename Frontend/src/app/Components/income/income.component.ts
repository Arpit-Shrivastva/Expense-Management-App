import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IncomeService } from 'src/app/Services/income.service';

@Component({
  selector: 'app-income',
  templateUrl: './income.component.html',
  styleUrls: ['./income.component.css']
})
export class IncomeComponent implements OnInit {

  incomeForm!: FormGroup;
  totalIncome: number | null = null;

  constructor(private fb: FormBuilder, private incomeService: IncomeService) { }

  ngOnInit(): void {
    this.incomeForm = this.fb.group({
      amount: [0, [Validators.required, Validators.min(1)]],
      month: ['', Validators.required]
    });

    const currentMonth = this.getCurrentMonth();
    this.incomeForm.get('month')?.setValue(currentMonth);
    this.fetchTotalIncome(currentMonth);

    this.incomeForm.get('month')?.valueChanges.subscribe((month) => {
      if (month) this.fetchTotalIncome(month);
    });
  }

  getCurrentMonth(): string {
    const now = new Date();
    return `${now.getFullYear()}-${(now.getMonth() + 1).toString().padStart(2, '0')}`;
  }


  onSubmit(): void {
    if (this.incomeForm.valid) {
      const selectedMonth = this.incomeForm.get('month')?.value;

      // Step 1: Check if income already exists for this month
      this.incomeService.getTotalIncomeForMonth(selectedMonth).subscribe({
        next: (existingIncome) => {
          if (existingIncome > 0) {
            alert(`❌ Income already exists for ${selectedMonth}. Only one income per month is allowed.`);
          } else {
            // Step 2: Proceed to add income
            this.incomeService.addIncome(this.incomeForm.value).subscribe({
              next: () => {
                alert('✅ Income added successfully');
                this.fetchTotalIncome(selectedMonth);
                this.incomeForm.reset();
              },
              error: (err) => console.error('❌ Failed to add income:', err)
            });
          }
        },
        error: (err) => {
          console.error('❌ Error checking existing income:', err);
          alert('⚠️ Unable to verify existing income for this month. Please try again.');
        }
      });
    }
  }

  fetchTotalIncome(month: string): void {
    this.incomeService.getTotalIncomeForMonth(month).subscribe({
      next: (total) => this.totalIncome = total,
      error: (err) => console.error('❌ Failed to fetch income:', err)
    });
  }

}
