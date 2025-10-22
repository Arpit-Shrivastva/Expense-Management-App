import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './Components/login/login.component';
import { RegisterComponent } from './Components/register/register.component';
import { DashboardComponent } from './Components/dashboard/dashboard.component';
import { AdminDashboardComponent } from './Components/admin-dashboard/admin-dashboard.component';
import { authGuard } from './Guards/auth.guard';
import { ExpensesComponent } from './Components/expenses/expenses.component';
import { BudgetComponent } from './Components/budget/budget.component';
import { ProfileComponent } from './Components/profile/profile.component';
import { DashboardLayoutComponent } from './Components/dashboard-layout/dashboard-layout.component';
import { SpentBudgetComponent } from './Components/spent-budget/spent-budget.component';
import { IncomeComponent } from './Components/income/income.component';
import { UserListComponent } from './Components/user-list/user-list.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  {
    path: 'layout',
    component: DashboardLayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'transactions', component: ExpensesComponent },
      { path: 'budget', component: BudgetComponent },
      { path: 'settings', component: ProfileComponent },
      { path: 'spended-budget', component: SpentBudgetComponent },
      { path: 'income', component: IncomeComponent },

      // 👇 Only admin can access this
      {
        path: 'users',
        component: UserListComponent,
        canActivate: [authGuard],
        data: { role: 'ROLE_ADMIN' }
      },
      {
        path: 'admin-dashboard',
        component: AdminDashboardComponent,
        canActivate: [authGuard],
        data: { role: 'ROLE_ADMIN' }
      }
    ]
  },



  { path: '**', redirectTo: 'login' }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
