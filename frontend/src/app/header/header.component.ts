import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login.service';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  userRole: string = ''; // Add the userRole property here


  constructor(private loginService: LoginService, private router: Router) {}

  isLoggedIn(): boolean {
    return !!localStorage.getItem('access_token');
  }
  logout(): void {
    // Clear the access_token from LocalStorage
    this.loginService.logout();

    // Redirect to the login page after logout
    this.router.navigate(['/home']);
  }
  ngOnInit(): void {
    this.userRole = localStorage.getItem('role') || '';
  }
}
