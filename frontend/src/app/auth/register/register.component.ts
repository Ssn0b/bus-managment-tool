import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface UserDTO {
  username: string;
  firstname: string;
  lastname: string;
  email: string;
  password: string;
  role: string;
}

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  formData: UserDTO = {
    username: '',
    firstname: '',
    lastname: '',
    email: '',
    password: '',
    role: 'USER'
  };

  constructor(private http: HttpClient) {}

  registerUser() {
    this.http.post<any>('http://localhost:8080/api/v1/auth/register', this.formData).subscribe(
      (response) => {
        // Handle successful response
        console.log('Registration successful:', response);
        // Optionally, you can show a success message or navigate to another page
      },
      (error) => {
        // Handle error
        console.error('Registration error:', error);
        // Optionally, you can show an error message or handle specific errors
      }
    );
  }
}
