import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { PopupDialogComponent } from 'src/app/popup-dialog/popup-dialog.component';
import { AxiosService } from 'src/services/axios-service/axios.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
  registrationForm: FormGroup;

  constructor(private fb: FormBuilder, private axiosService: AxiosService, private router: Router, private dialog: MatDialog) {
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
          this.axiosService
            .request('POST', `/auth/register`, formData)
            .then((response: any) => {
              this.openPopupDialog('Registration successful', 'Confirmation was sent to your email!');
              this.router.navigate(['/authenticate']);
              console.log('Registration successful', response);
            })
            .catch((error: any) => {
              this.openPopupDialog('Registration failed', 'Please try again.');
            });
    }
  }
  openPopupDialog(title: string, message: string): void {
    const dialogRef = this.dialog.open(PopupDialogComponent, {
      width: '300px',
      data: { title, message },
    });
  
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}