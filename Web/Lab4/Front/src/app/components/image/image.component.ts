import { BreakpointObserver } from '@angular/cdk/layout';
import { Component, EventEmitter, Output } from '@angular/core';


@Component({
  selector: 'app-image',
  imports: [],
  templateUrl: './image.component.html',
  styleUrl: './image.component.css'
})


export class ImageComponent {
  currentMode = "desktop";
  isMirrored: boolean = false;
  private sourceSize: number = 220;
  size: number = 440;
  radius: number = this.sourceSize / 2.75;
  startPoint: number = this.sourceSize / 2;
  scale: number = this.size / this.sourceSize;
  @Output() pointClicked = new EventEmitter<{ x: number, y: number }>();


  constructor(private breakpointObserver: BreakpointObserver) {
    this.breakpointObserver.observe([
      '(max-width: 650px)',
      '(min-width: 651px) and (max-width: 1102px)',
      '(min-width: 1103px)'
    ]).subscribe(result => {
      if (result.breakpoints['(max-width: 650px)']) {
        this.currentMode = "mobile";
        this.size = 300;
        if (window.innerWidth < 300) this.size = window.innerWidth * 9 / 10;
      } else if (result.breakpoints['(min-width: 651px) and (max-width: 1102px)']) {
        this.currentMode = "tablet";
        this.size = 440;
      } else if (result.breakpoints['(min-width: 1103px)']) {
        this.currentMode = "desktop";
        this.size = 440;
      }
      this.scale = this.size / this.sourceSize;
    });
  }


  get transform(): string {
    return `scale(${this.scale})`;
  }


  onSvgClick(event: MouseEvent): void {
    const targetElement = event.target as Element;
    const svg = targetElement.closest("svg") as SVGSVGElement;
    if (!svg) return;

    let point = svg.createSVGPoint();
    point.x = event.clientX;
    point.y = event.clientY;
    let ctm = svg.getScreenCTM();
    if (!ctm) return;
    let svgPoint = point.matrixTransform(ctm.inverse());
    let x = svgPoint.x - this.size / 2;
    let y = this.size / 2 - svgPoint.y;
    x = x / (this.radius * this.scale);
    y = y / (this.radius * this.scale);
    this.pointClicked.emit({ x: x, y: y });
  }


  addPoint(point: { x: number, y: number, r: number }, color: string) {
    let circles = document.getElementById("circles");
    if (!circles) { return; }
    if (point.r < 0) point.r = - point.r;
    let pointSize = 2;
    switch (this.currentMode) {
      case ('mobile'): {
        pointSize = 4;
        break;
      }
      case ('tablet'): {
        pointSize = 4;
        break;
      }
      case ('desktop'): {
        pointSize = 2;
        break;
      }
    }
    point.x = point.x / point.r;
    point.y = point.y / point.r;
    point.x = point.x * (this.radius * this.scale);
    point.y = point.y * (this.radius * this.scale);
    point.x = point.x + this.size / 2;
    point.y = this.size / 2 - point.y;
    circles.innerHTML += `<circle cx="${point.x}" cy="${point.y}" r="${pointSize}" fill="${color}" />`
    console.log("Нарисована точка:", point.x, point.y);
  }


  removeAll() {
    let circles = document.getElementById("circles");
    if (!circles) { return; }
    circles.innerHTML = "";
  }


  changeRadius(points: { x: number; y: number; r: number; result: boolean; createdTime: string }[], radius: number) {
    this.removeAll();
    let color;
    points.forEach(element => {
      this.checkPoint({ x: element.x, y: element.y }, radius) ? color = "green" : color = "red";
      this.addPoint({ x: element.x, y: element.y, r: radius }, color);
    });
  }


  mirror() {
    let area = document.getElementById("area");
    if (!area) { return; }
    if (!this.isMirrored) {
      area.innerHTML = '<rect width="80" height="40" fill="#3cb0fe" x="110" y="110" /><path fill="#3cb0fe" d="M110 110 H 30 L110 150 Z" /><path d="M 110,110 H 30 A 80,80 0 0,1 110,30" fill="#3cb0fe" />';
    }
    else {
      area.innerHTML = '<rect width="80" height="40" fill="#3cb0fe" x="30" y="70" /><path fill="#3cb0fe" d="M110 110 H 190 L110 70 Z" /><path d="M 110,110 H 190 A 80,80 0 0,1 110,190" fill="#3cb0fe" />';
    }
  }


  checkPoint(point: { x: number, y: number }, radius: number): boolean {
    if (this.isMirrored) {
      if (point.x <= 0 && point.y >= 0 && point.x >= - radius && point.y <= radius / 2) return true;
      else if (point.x >= 0 && point.y >= 0 && point.y <= - point.x / 2 + radius / 2) return true;
      else if (point.x >= 0 && point.y <= 0 && (point.x * point.x + point.y * point.y < radius * radius)) return true;
      return false;
    }
    if (point.x >= 0 && point.y <= 0 && point.x <= radius && point.y >= -radius / 2) return true;
    else if (point.x <= 0 && point.y <= 0 && point.y >= -point.x / 2 - radius / 2) return true;
    else if (point.x <= 0 && point.y >= 0 && (point.x * point.x + point.y * point.y < radius * radius)) return true;
    return false;
  }

}
