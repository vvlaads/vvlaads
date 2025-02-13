import { Component, Input } from '@angular/core';


@Component({
  selector: 'app-error-message',
  imports: [],
  templateUrl: './error-message.component.html',
  styleUrl: './error-message.component.css'
})


export class ErrorMessageComponent {
  @Input()
  public message: string = "";
}
