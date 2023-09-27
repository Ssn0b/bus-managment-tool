import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InactivityService {
  private inactivityTimer: any;

  constructor() {}

  startInactivityTimer() {
    this.stopInactivityTimer(); // Перед запуском нового таймера спочатку зупиніть попередній, якщо він існує.

    this.inactivityTimer = setTimeout(() => {
      // Код, який виконується після 15 хвилин неактивності.
      localStorage.removeItem('userRole');
      localStorage.removeItem('accessToken');

    }, 15 * 60 * 1000); // 15 хвилин в мілісекундах
  }

  stopInactivityTimer() {
    if (this.inactivityTimer) {
      clearTimeout(this.inactivityTimer);
    }
  }
}
