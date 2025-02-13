import { BreakpointObserver } from '@angular/cdk/layout';
import { CommonModule, NgFor } from '@angular/common';
import { Component } from '@angular/core';


@Component({
  selector: 'app-table',
  standalone: true,
  imports: [NgFor, CommonModule],
  templateUrl: './table.component.html',
  styleUrl: './table.component.css'
})


export class TableComponent {
  rows: { x: number; y: number; r: number; result: boolean; createdTime: string }[] = [];

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

  addRow(x: number, y: number, r: number, result: boolean, createdTime: string): void {
    switch (this.currentMode) {
      case ('mobile'): {
        x = Math.round(x * 100) / 100;
        y = Math.round(y * 100) / 100;
        r = Math.round(r * 100) / 100;
        break;
      }
      case ('tablet'): {
        x = Math.round(x * 1000000) / 1000000;
        y = Math.round(y * 1000000) / 1000000;
        r = Math.round(r * 1000000) / 1000000;
        break;
      }
    }
    const newRow = { x, y, r, result, createdTime };
    this.rows.push(newRow);
  }
}
