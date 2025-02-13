import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { ErrorMessageComponent } from "../error-message/error-message.component";
import { Router } from '@angular/router';
import { BreakpointObserver } from '@angular/cdk/layout';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-auth-form',
  imports: [FormsModule, ErrorMessageComponent, CommonModule],
  templateUrl: './auth-form.component.html',
  styleUrl: './auth-form.component.css'
})


export class AuthFormComponent {
  currentMode = "desktop";
  errorMessage: string = '';
  user = { login: '', password: '' };


  constructor(private authService: AuthService, private router: Router, private breakpointObserver: BreakpointObserver) {
    this.breakpointObserver.observe([
      '(max-width: 650px)',
      '(min-width: 651px) and (max-width: 1102px)',
      '(min-width: 1103px)'
    ]).subscribe(result => {
      if (result.breakpoints['(max-width: 650px)']) {
        this.currentMode = "mobile";
      } else if (result.breakpoints['(min-width: 651px) and (max-width: 1102px)']) {
        this.currentMode = "tablet";
      } else if (result.breakpoints['(min-width: 1103px)']) {
        this.currentMode = "desktop";
      }
    });


    this.authService.isAuthenticated$.subscribe(isAuthenticated => {
      if (isAuthenticated) {
        this.router.navigate(['/home']);
      }
    });

    this.authService.errorMessage$.subscribe(message => {
      this.errorMessage = message;
    })
  }


  submit(): void {
    this.authService.login(this.user);
  }
}
