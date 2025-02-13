import { BreakpointObserver } from '@angular/cdk/layout';
import { CommonModule } from '@angular/common';
import { Component, forwardRef, Input } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';


@Component({
  selector: 'app-input-radio',
  imports: [CommonModule],
  templateUrl: './input-radio.component.html',
  styleUrl: './input-radio.component.css',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputRadioComponent),
      multi: true
    }
  ]
})


export class InputRadioComponent implements ControlValueAccessor {
  @Input()
  title: string = "";
  @Input()
  name: string = ""
  @Input()
  values: string[] = [];

  selectedValue: string = ""

  currentMode = "desktop";

  countValue = 0;

  constructor(private breakpointObserver: BreakpointObserver) {
    this.breakpointObserver.observe([
      '(max-width: 650px)',
      '(min-width: 651px) and (max-width: 1102px)',
      '(min-width: 1103px)'
    ]).subscribe(result => {
      this.countValue = Math.round(window.innerWidth / 80);
      if (this.countValue < 1) this.countValue = 1;
      if (result.breakpoints['(max-width: 650px)']) {
        this.currentMode = "mobile";
      } else if (result.breakpoints['(min-width: 651px) and (max-width: 1102px)']) {
        this.currentMode = "tablet";
      } else if (result.breakpoints['(min-width: 1103px)']) {
        this.currentMode = "desktop";
        this.countValue = 6;
      }
    });
  }

  onChange = (value: string) => { };

  onTouched = () => { };

  writeValue(value: string): void {
    this.selectedValue = value;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  selectValue(value: string): void {
    this.selectedValue = value;
    this.onChange(value);
    this.onTouched();
  }
}
