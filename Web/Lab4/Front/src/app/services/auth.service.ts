import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, catchError, map, Observable, of, tap } from 'rxjs';


@Injectable({
  providedIn: 'root'
})


export class AuthService {
  private apiUrl = "http://localhost:8080/Lab4/api/auth";

  private errorSubject = new BehaviorSubject<string>('');
  errorMessage$ = this.errorSubject.asObservable();

  private isAuthenticatedSubject = new BehaviorSubject<boolean>(this.hasSession());
  isAuthenticated$ = this.isAuthenticatedSubject.asObservable();


  constructor(private http: HttpClient, private router: Router) { }


  private hasSession(): boolean {
    return sessionStorage.getItem("sessionId") !== null;
  }


  login(user: { login: string, password: string }): void {
    console.log("Попытка авторизации");
    this.http.post(`${this.apiUrl}/login`, user, { withCredentials: true }).subscribe({
      next: (response: any) => {
        if (response.sessionId) {
          sessionStorage.setItem("sessionId", response.sessionId);
          sessionStorage.setItem("userId", response.userId);
          this.isAuthenticatedSubject.next(true);
          console.log('Успешная авторизация, перенаправление в /home');
          this.router.navigate(['/home']);
        }
      },
      error: (error: HttpErrorResponse) => {
        console.error(error.message);
        if (error.status === 401) {
          this.errorSubject.next("Неверный логин или пароль");
        } else {
          this.errorSubject.next("Ошибка авторизации");
        }
      }
    });
  }


  logout(): void {
    console.log("Попытка выхода из аккаунта");
    const sessionId = sessionStorage.getItem("sessionId");

    if (!sessionId) {
      this.errorSubject.next("Сессия не найдена");
      return;
    }

    this.http.post(`${this.apiUrl}/logout`, {}, {
      headers: { "X-Session-Id": sessionId },
      withCredentials: true
    }).subscribe({
      next: () => {
        console.log("Успешный выход");
      },
      error: (error: HttpErrorResponse) => {
        console.error(error.error);
        this.errorSubject.next("Ошибка выхода из системы");
      }
    });

    sessionStorage.removeItem("sessionId");
    sessionStorage.removeItem("userId");
    this.isAuthenticatedSubject.next(false);
    console.log("Перенаправление в /login");
    this.router.navigate(['/login']);
  }


  checkSession(): Observable<boolean> {
    const sessionId = sessionStorage.getItem("sessionId");
    if (!sessionId) {
      this.isAuthenticatedSubject.next(false);
      return of(false);
    }

    return this.http.get(`${this.apiUrl}/session`, { headers: { "X-Session-Id": sessionId }, withCredentials: true }).pipe(
      tap((response: any) => {
        if (response.userId) {
          this.isAuthenticatedSubject.next(true);
        } else {
          this.isAuthenticatedSubject.next(false);
        }
      }),
      map(response => !!response.userId),
      catchError(() => {
        this.isAuthenticatedSubject.next(false);
        return of(false);
      })
    );
  }
}
