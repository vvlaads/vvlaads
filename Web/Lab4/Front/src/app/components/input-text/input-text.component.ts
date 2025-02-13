import { BreakpointObserver } from '@angular/cdk/layout';
import { CommonModule } from '@angular/common';
import { Component, forwardRef, Input } from '@angular/core';
import { ControlValueAccessor, FormsModule, NG_VALUE_ACCESSOR } from '@angular/forms';


@Component({
  selector: 'app-input-text',
  imports: [FormsModule, CommonModule],
  templateUrl: './input-text.component.html',
  styleUrl: './input-text.component.css',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputTextComponent),
      multi: true
    }
  ]
})


export class InputTextComponent implements ControlValueAccessor {
  @Input()
  title: string = "";
  @Input()
  name: string = ""
  @Input()
  placeholder: string = ""

  value: string = '';

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
    });
  }
  onChange = (value: string) => { };
  onTouched = () => { };

  writeValue(value: string): void {
    this.value = value;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }
}
