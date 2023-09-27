import { Component, ViewChild, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatSidenav } from '@angular/material/sidenav';
import { InactivityService } from 'src/services/inactivity-service/inactivity.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{
  @ViewChild(MatSidenav) sidenav!: MatSidenav;
  userRole: string | null;

  constructor(private router: Router, private inactivityService: InactivityService) {
    this.userRole = localStorage.getItem('userRole');
    console.log("JA V AHUJI" + localStorage.getItem('userRole'));
    window.addEventListener('beforeunload', (event) => {
      localStorage.removeItem('userRole');
    });
  }
  ngOnInit() {
    this.inactivityService.startInactivityTimer();
  }

  logout() {
    localStorage.removeItem('userRole');
    this.inactivityService.stopInactivityTimer();
  }

  onUserActivity() {
    this.inactivityService.startInactivityTimer();
  }

}
