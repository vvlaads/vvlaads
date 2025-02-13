import { Injectable } from '@angular/core';


@Injectable({
  providedIn: 'root'
})


export class ValidationService {
  constructor() { }


  validateX(x: string): string {
    const values: number[] = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2];
    if (!x) {
      return "Выберите значение X";
    }
    if (isNaN(Number(x))) {
      return "Значение X должно быть числом";
    }
    if (values.includes(Number(x))) {
      return "";
    }
    return "Неверно выбрано значение";
  }


  validateY(y: string): string {
    if (!y) {
      return "Введите значение Y";
    }
    y = y.replace(",", ".");
    if (isNaN(Number(y)) || y.trim() == "") {
      return "Значение Y должно быть числом"
    }
    if (Number(y) <= -3 || Number(y) >= 3) {
      return "Значение должно быть в интервале (-3, 3)";
    }
    return "";
  }


  validateR(r: string): string {
    const values: number[] = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2];
    if (!r) {
      return "Выберите значение R";
    }
    if (isNaN(Number(r))) {
      return "Значение R должно быть числом";
    }
    if (values.includes(Number(r))) {
      if (Number(r) == 0) return "Выберите ненулевой радиус";
      return "";
    }
    return "Неверно выбрано значение";
  }
}
