import { Component,ViewChild  } from '@angular/core';
import { Router } from '@angular/router';
import { MatSidenav } from '@angular/material/sidenav';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  @ViewChild(MatSidenav) sidenav!: MatSidenav;

  userRole: string = '';


  constructor(private router: Router) {}
}
