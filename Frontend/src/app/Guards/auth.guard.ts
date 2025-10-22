import { inject } from '@angular/core';
import { CanActivateFn, Router, ActivatedRouteSnapshot } from '@angular/router';

export const authGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state) => {
  const router = inject(Router);
  const token = localStorage.getItem('token');
  const userRole = localStorage.getItem('role'); // e.g., 'ROLE_ADMIN'

  if (!token) {
    router.navigate(['/login'], {
      queryParams: { returnUrl: state.url }
    });
    return false;
  }

  const requiredRole = route.data['role']; // Optional role restriction

  if (!requiredRole || userRole === requiredRole) {
    return true;
  }

  // Role mismatch
  router.navigate(['/dashboard']); // or a 403 page
  return false;
};
