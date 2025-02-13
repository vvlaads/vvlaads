import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { HeaderComponent } from "../../components/header/header.component";
import { InputRadioComponent } from "../../components/input-radio/input-radio.component";
import { ErrorMessageComponent } from "../../components/error-message/error-message.component";
import { ImageComponent } from "../../components/image/image.component";
import { InputTextComponent } from "../../components/input-text/input-text.component";
import { TableComponent } from "../../components/table/table.component";
import { ExitLinkComponent } from "../../components/exit-link/exit-link.component";
import { FormsModule, NgForm } from '@angular/forms';
import { ValidationService } from '../../services/validation.service';
import { SendFormService } from '../../services/send-form.service';
import { BreakpointObserver } from '@angular/cdk/layout';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-home',
  imports: [CommonModule, FormsModule, HeaderComponent, InputRadioComponent, ErrorMessageComponent, ImageComponent, InputTextComponent, TableComponent, ExitLinkComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})


export class HomeComponent implements AfterViewInit {
  @ViewChild(TableComponent) tableComponent!: TableComponent;
  @ViewChild(ImageComponent) imageComponent!: ImageComponent;

  currentMode = 'desktop';

  errorMessageX: string = "";
  errorMessageY: string = "";
  errorMessageR: string = "";

  point = { x: "", y: "", r: "" };
  points: { x: number, y: number, r: number, result: boolean, createdTime: string }[] = [];


  constructor(private validator: ValidationService, private sendFormService: SendFormService, private breakpointObserver: BreakpointObserver) {
    this.breakpointObserver.observe([
      '(max-width: 650px)',
      '(min-width: 651px) and (max-width: 1102px)',
      '(min-width: 1103px)'
    ]).subscribe(result => {
      let oldMode = this.currentMode;
      if (result.breakpoints['(max-width: 650px)']) {
        this.currentMode = "mobile";
      } else if (result.breakpoints['(min-width: 651px) and (max-width: 1102px)']) {
        this.currentMode = "tablet";
      } else if (result.breakpoints['(min-width: 1103px)']) {
        this.currentMode = "desktop";
      }
      if (oldMode != this.currentMode) {
        this.ngAfterViewInit();
      }
      console.log("Изменен режим:", this.currentMode);
    });
  }


  onRChange(newR: string): void {
    console.log('Значение R изменено:', newR);
    if (Number(newR) != 0) {
      Number(newR) < 0 ? this.imageComponent.isMirrored = true : this.imageComponent.isMirrored = false;
      let value = Number(newR);
      if (value < 0) {
        value = -value;
      }
      this.imageComponent.mirror();
      this.imageComponent.changeRadius(this.tableComponent.rows, value);
    }
  }


  submit(form: NgForm): void {
    this.errorMessageX = this.validator.validateX(this.point.x);
    this.errorMessageY = this.validator.validateY(this.point.y);
    this.errorMessageR = this.validator.validateR(this.point.r);

    if (!this.errorMessageX && !this.errorMessageY && !this.errorMessageR) {
      console.log("Отправка точки", {
        x: this.point.x,
        y: this.point.y,
        r: this.point.r
      });
      let value = Number(this.point.r);
      if (value < 0) value = - value;
      this.sendFormService.send(this.point, (response: any) => {
        if (response.message) {
          let color;
          console.log(response.message);
          const { x, y, r, result, createdTime } = response.point;
          this.tableComponent.addRow(x, y, r, result, createdTime);
          this.imageComponent.checkPoint({ x: response.point.x, y: response.point.y }, value) ? color = "green" : color = "red";
          this.imageComponent.addPoint({ x: response.point.x, y: response.point.y, r: Number(this.point.r) }, color);
        }
      });
    }
  }


  ngAfterViewInit(): void {
    this.sendFormService.getPoints().subscribe({
      next: (response) => {
        console.log(response);
        if (this.tableComponent) {
          this.tableComponent.rows = response.data.points;
          this.tableComponent.rows.forEach(element => {
            switch (this.currentMode) {
              case ('mobile'): {
                element.x = Math.round(element.x * 100) / 100;
                element.y = Math.round(element.y * 100) / 100;
                element.r = Math.round(element.r * 100) / 100;
                break;
              }
              case ('tablet'): {
                element.x = Math.round(element.x * 1000000) / 1000000;
                element.y = Math.round(element.y * 1000000) / 1000000;
                element.r = Math.round(element.r * 1000000) / 1000000;
                break;
              }
            }
            let value = element.r;
            if (value < 0) value = - value;
            let color;
            this.imageComponent.checkPoint({ x: element.x, y: element.y }, value) ? color = "green" : color = "red";
            this.imageComponent.addPoint({ x: element.x, y: element.y, r: element.r }, color);
          });
        } else {
          console.error('tableComponent не инициализирован!');
        }
      },
      error: (error) => {
        console.error('Ошибка при загрузке точек:', error);
      }
    });
  }


  onPointClicked(point: { x: number, y: number }) {
    console.log("Нажата точка:", point);
    this.errorMessageR = this.validator.validateR(this.point.r);
    if (!this.errorMessageR) {
      let value = Number(this.point.r);
      if (value < 0) value = - value;
      point.x = point.x * value;
      point.y = point.y * value;
      console.log("Исправлена точка:", point);

      this.sendFormService.send({ x: point.x.toString(), y: point.y.toString(), r: this.point.r }, (response: any) => {
        if (response.message) {
          let color;
          console.log(response.message);
          const { x, y, r, result, createdTime } = response.point;
          this.tableComponent.addRow(x, y, r, result, createdTime);
          this.imageComponent.checkPoint({ x: point.x, y: point.y }, value) ? color = "green" : color = "red";
          this.imageComponent.addPoint({ x: point.x, y: point.y, r: Number(this.point.r) }, color);
        }
      });
    }
  }
}