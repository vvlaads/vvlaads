import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})


export class SendFormService {
  private apiUrl: string = "http://localhost:8080/Back/api/point";


  constructor(private http: HttpClient) { }


  private getHeaders(): HttpHeaders {
    const sessionId = sessionStorage.getItem("sessionId");
    return new HttpHeaders({
      "X-Session-Id": sessionId || ""
    });
  }


  send(point: { x: string, y: string, r: string }, callback: (response: any) => void): void {
    this.http.post(this.apiUrl, point, { headers: this.getHeaders(), withCredentials: true }).subscribe({
      next: (response: any) => {
        callback(response);
      },
      error: (error: HttpErrorResponse) => {
        console.error(error.error);
      }
    });
  }


  getPoints(): Observable<any> {
    return this.http.get(this.apiUrl, { headers: this.getHeaders(), withCredentials: true });
  }
}
