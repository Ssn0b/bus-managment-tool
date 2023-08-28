import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  loginError: string = '';

  constructor(private loginService: LoginService, private router: Router) {}

  onSubmit(): void {
    this.loginService.login(this.username, this.password).subscribe(
      (response) => {
        // Login successful
        // Save the JWT token in localStorage or sessionStorage
        localStorage.setItem('access_token', response.accessToken);
        localStorage.setItem('role', response.role); // Set the user role in localStorage

        // Redirect to a secure page (e.g., home page)
        this.router.navigate(['/home']);
      },
      (error) => {
        // Login failed
        this.loginError = 'Invalid credentials. Please try again.';
      }
    );
  }
}
