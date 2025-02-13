import { Component } from '@angular/core';
import { HeaderComponent } from "../../components/header/header.component";
import { AuthFormComponent } from "../../components/auth-form/auth-form.component";
import { BreakpointObserver } from '@angular/cdk/layout';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-login',
  imports: [HeaderComponent, AuthFormComponent, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})


export class LoginComponent {
  currentMode = "desktop";

  constructor(private breakpointObserver: BreakpointObserver) {
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
      console.log("Изменен режим:", this.currentMode);
    });
  }
}
