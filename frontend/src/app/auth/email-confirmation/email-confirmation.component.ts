import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { AxiosService } from 'src/services/axios-service/axios.service';
import { PopupDialogComponent } from 'src/app/popup-dialog/popup-dialog.component';

@Component({
  selector: 'app-email-confirmation',
  templateUrl: './email-confirmation.component.html',
  styleUrls: ['./email-confirmation.component.css']
})
export class EmailConfirmationComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private axiosService: AxiosService,
    private dialog: MatDialog,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const token = params['token'];
      this.confirmEmail(token);
    });
  }

  
  confirmEmail(token: string): void {
    this.axiosService
      .request('GET', `/auth/confirm?token=${token}`, {})
      .then((response: any) => {
        this.openPopupDialog('Email Confirmed', 'Your email has been successfully confirmed.');
        this.router.navigate(['/authenticate']);
      })
      .catch((error: any) => {
        this.openPopupDialog('Confirmation Error', 'Invalid confirmation token. Please try again.');
        this.router.navigate(['/home']);      
      });
  }
  openPopupDialog(title: string, message: string): void {
    this.dialog.open(PopupDialogComponent, {
      data: { title, message },
      width: '300px'
    });
  }
}
