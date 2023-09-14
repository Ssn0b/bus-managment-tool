import { Component, OnInit,HostListener } from '@angular/core';
import { InactivityService } from 'src/services/inactivity-service/inactivity.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title = 'frontend';

  constructor(private inactivityService: InactivityService) { }
  ngOnInit() {
    // Start the inactivity timer with a callback (e.g., clearLocalStorage) and timeout (15 minutes).
    this.inactivityService.startInactivityTimer(() => {
      this.clearLocalStorage();
    }, 900000); // 900000 milliseconds = 15 minutes
  }
  @HostListener('window:mousemove', ['$event'])
  @HostListener('window:keydown', ['$event'])
  onUserActivity(event: Event): void {
    this.inactivityService.resetInactivityTimer();
  }

  private clearLocalStorage() {
    // Clear all items in localStorage.
    localStorage.clear();
  }
}
