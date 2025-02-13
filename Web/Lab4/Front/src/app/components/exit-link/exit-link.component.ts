import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';


@Component({
  selector: 'app-exit-link',
  imports: [],
  templateUrl: './exit-link.component.html',
  styleUrl: './exit-link.component.css'
})


export class ExitLinkComponent {
  constructor(private authService: AuthService) { }

  exit() {
    this.authService.logout();
  }
}
