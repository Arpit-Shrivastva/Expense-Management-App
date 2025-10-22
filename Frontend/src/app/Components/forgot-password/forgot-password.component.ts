import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { ResetpasswordService } from 'src/app/Services/resetpassword.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {

  forgotForm: FormGroup;
resetForm: FormGroup;
successMessage = '';
errorMessage = '';
resetSuccess = '';
resetError = '';
showResetForm = false;

constructor(
  private fb: FormBuilder,
  private authService: ResetpasswordService,
  private dialogRef: MatDialogRef<ForgotPasswordComponent>
) {
  this.forgotForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
  });

  this.resetForm = this.fb.group({
    token: ['', Validators.required],
    newPassword: ['', [Validators.required, Validators.minLength(6)]],
  });
}

onSubmit(): void {
  if (this.forgotForm.valid) {
    const { email } = this.forgotForm.value;
    this.authService.forgotPassword(email).subscribe({
      next: (res) => {
        this.successMessage = res;
        this.errorMessage = '';
        setTimeout(() => {
          this.showResetForm = true; 
        }, 4000);
      },
      error: (err) => {
        this.errorMessage = err.error;
        this.successMessage = '';
      },
    });
  }
}

onReset(): void {
  if (this.resetForm.valid) {
    const { token, newPassword } = this.resetForm.value;
    this.authService.resetPassword(token, newPassword).subscribe({
      next: (res) => {
        this.resetSuccess = res;
        this.resetError = '';
      },
      error: (err) => {
        this.resetError = err.error;
        this.resetSuccess = '';
      },
    });
  }
}

close(): void {
  this.dialogRef.close();
}


}
