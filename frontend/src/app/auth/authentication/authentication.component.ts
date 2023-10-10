  import { Component } from '@angular/core';
  import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
  import { Router } from '@angular/router';
import { AxiosService } from 'src/services/axios-service/axios.service';
import { PopupDialogComponent } from 'src/app/popup-dialog/popup-dialog.component';
import { AuthenDTO } from 'src/dto/authen-dto.model';

  @Component({
    selector: 'app-authentication',
    templateUrl: './authentication.component.html',
    styleUrls: ['./authentication.component.css']
  })
  export class AuthenticationComponent {
    authen: AuthenDTO | undefined;
    authenticationForm: FormGroup;
    errorMessage: string = '';

    constructor(private fb: FormBuilder, private axiosService: AxiosService, private router: Router, private dialog: MatDialog) {
      this.authenticationForm = this.fb.group({
        username: ['', Validators.required],
        password: ['', Validators.required]
      });
    }

    onSubmit() {

      if (this.authenticationForm.valid) {
        const formData = this.authenticationForm.value;
        
        this.axiosService
            .request('POST', `/auth/authenticate`, formData)
            .then((response: any) => {
              this.authen = response.data;
              console.log(this.authen);
              if (this.authen ) {
                localStorage.setItem('accessToken', this.authen.access_token);
                localStorage.setItem('userRole', this.authen.role);
                this.router.navigate(['/home']);
              }else {
                this.openPopupDialog('Login failed', 'Please try again.');
              }
            })
            .catch((error: any) => {
              if (error && error.response && error.response.status === 409) {
                this.openPopupDialog('Login failed', 'Email is not confirmed. Please confirm email.');
              } else {
              this.openPopupDialog('Login failed', 'Please try again.');
              }
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

