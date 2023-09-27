  import { Component } from '@angular/core';
  import { FormBuilder, FormGroup, Validators } from '@angular/forms';
  import { HttpClient } from '@angular/common/http';
  import { Router } from '@angular/router';

  @Component({
    selector: 'app-authentication',
    templateUrl: './authentication.component.html',
    styleUrls: ['./authentication.component.css']
  })
  export class AuthenticationComponent {
    authenticationForm: FormGroup;
    errorMessage: string = '';

    constructor(private fb: FormBuilder, private http: HttpClient, private router: Router) {
      this.authenticationForm = this.fb.group({
        username: ['', Validators.required],
        password: ['', Validators.required]
      });
    }

    onSubmit() {
      if (this.authenticationForm.valid) {
        const formData = this.authenticationForm.value;
        
        this.http.post('http://localhost:8080/api/v1/auth/authenticate', formData).subscribe({
          next: (response: any) => {
            console.log('Authentication successful', response);

            const accessToken = response.access_token;
            const userRole = response.role;

            localStorage.setItem('accessToken', accessToken);
            localStorage.setItem('userRole', userRole);
            this.router.navigate(['/home']); 
          },
          error: (error: any) => {
            console.error('Authentication error', error);
            this.errorMessage = 'Authentication failed. Please check your credentials.';
          }
    });
          
          
      }
    }
  }
