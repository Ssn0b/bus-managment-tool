import { Injectable, HostListener } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InactivityService {
  private inactivityTimeout: any;

  constructor() { }

  startInactivityTimer(callback: () => void, timeout: number) {
    clearTimeout(this.inactivityTimeout);
    this.inactivityTimeout = setTimeout(() => {
      callback(); // Execute the callback function (e.g., clearLocalStorage) after the specified timeout.
    }, timeout);
  }

  resetInactivityTimer() {
    clearTimeout(this.inactivityTimeout);
  }

  @HostListener('window:beforeunload', ['$event'])
  onBeforeUnload(event: Event): void {
    // Clear localStorage when the browser is closed.
    this.clearLocalStorage();
  }

  private clearLocalStorage() {
    localStorage.clear(); // Clear all items in localStorage.
  }
}
