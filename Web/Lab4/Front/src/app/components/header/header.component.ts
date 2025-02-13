import { BreakpointObserver, LayoutModule } from '@angular/cdk/layout';
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';


@Component({
  selector: 'app-header',
  imports: [LayoutModule, CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})


export class HeaderComponent {
  currentMode = "desktop";
  private fullName = "Силинцев Владислав Витальевич"
  private group = "P3214"
  private option = 32140000


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


  public getFullName() {
    return this.fullName;
  }


  public getGroup() {
    return this.group;
  }


  public getOption() {
    return this.option;
  }
}
