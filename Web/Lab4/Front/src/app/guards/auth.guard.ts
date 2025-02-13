import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { inject } from '@angular/core';
import { first, map, switchMap, tap } from 'rxjs';
import { of } from 'rxjs';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.isAuthenticated$.pipe(
    first(),
    switchMap(isAuthenticated => {
      if (isAuthenticated) {
        return of(true);
      } else {
        return authService.checkSession().pipe(
          tap(sessionValid => {
            if (!sessionValid) {
              router.navigate(['/login']);
            }
          }),
          map(sessionValid => sessionValid)
        );
      }
    })
  );
};
