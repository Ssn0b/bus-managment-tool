import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
  registrationForm: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient, private router: Router) {
    this.registrationForm = this.fb.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      username: ['',  Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      subscribe: [true],
    });
  }

  onSubmit() {
    if (this.registrationForm.valid) {
      // Extract form data
      const formData = {
        ...this.registrationForm.value,
        balance: "0.0",
        role: "USER"
      };
      console.log('Form Data:', formData);

      // Make an HTTP POST request to your backend
      this.http.post('http://localhost:8080/api/v1/auth/register', formData)
        .subscribe({
              next: (response) => {
                      this.router.navigate(['/authenticate']);
                      console.log('Registration successful', response);
              },
              error: (error) => {
                      console.error('Registration error', error);
              },
        });
    }
  }
}