import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/Services/auth.service';

@Component({
  selector: 'app-dashboard-layout',
  templateUrl: './dashboard-layout.component.html',
  styleUrls: ['./dashboard-layout.component.css']
})
export class DashboardLayoutComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) { }

  isSmallScreen: boolean = false;
  role: string | null = null;

  ngOnInit(): void {
    this.role = localStorage.getItem('role');
    this.checkScreenSize();
  }

  @HostListener('window:resize')
  onResize(): void {
    this.checkScreenSize();
  }

  checkScreenSize(): void {
    this.isSmallScreen = window.innerWidth < 768;
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/login']); 
  }
}