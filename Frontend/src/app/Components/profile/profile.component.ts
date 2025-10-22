import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserUpdateRequest } from 'src/app/Dto/UserUpdateRequest';
import { AuthService } from 'src/app/Services/auth.service';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  profileForm!: FormGroup;
  userId!: number;
  role: [{ value: ''; disabled: true; }] | undefined

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.profileForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: [''],
      role: [{ value: '', disabled: true }]  // role is read-only
    });

    const id = this.authService.getUserId();
    if (id) {
      this.userId = id;
      this.loadUserData();
    } else {
      console.error('❌ User ID not found in AuthService');
    }
  }

  loadUserData(): void {
    this.userService.getUserById(this.userId).subscribe({
      next: (user) => {
        this.profileForm.patchValue({
          username: user.username,
          email: user.email,
          role: user.role
        });
      },
      error: (err) => console.error('❌ Failed to fetch user:', err)
    });
  }

  onSubmit(): void {
    if (this.profileForm.valid && this.userId) {
      const updatedUser: UserUpdateRequest = {
        id: this.userId,
        username: this.profileForm.get('username')?.value,
        email: this.profileForm.get('email')?.value,
        password: this.profileForm.get('password')?.value
      };

      this.userService.updateUser(this.userId, updatedUser).subscribe({
        next: () => alert('✅ Profile updated successfully!'),
        error: (err) => console.error('❌ Update failed:', err)
      });
    }
  }
}
