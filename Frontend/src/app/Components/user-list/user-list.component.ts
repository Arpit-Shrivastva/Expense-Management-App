import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { AuthResponse } from 'src/app/Dto/auth-response.model';
import { AdminService } from 'src/app/Services/admin.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit{

  displayedColumns: string[] = ['username', 'email', 'role', 'actions'];
  dataSource = new MatTableDataSource<AuthResponse>([]);
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private adminService: AdminService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.adminService.getAllUsers().subscribe(users => {
      this.dataSource.data = users;
      this.dataSource.paginator = this.paginator;
    });
  }

  deleteUser(username: string): void {
    if (!confirm(`Are you sure you want to delete user "${username}"?`)) return;

    this.adminService.deleteUser(username).subscribe({
      next: (res) => {
        this.snackBar.open(`User "${username}" deleted successfully!`, 'Close', { duration: 3000 });
        this.loadUsers(); // reload after delete
      },
      error: (err) => {
        this.snackBar.open(`Failed to delete user "${username}"`, 'Close', { duration: 3000 });
      }
    });
  }

}
