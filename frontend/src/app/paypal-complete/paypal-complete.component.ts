import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AxiosService } from 'src/services/axios-service/axios.service';

@Component({
  selector: 'app-paypal-complete',
  templateUrl: './paypal-complete.component.html',
  styleUrls: ['./paypal-complete.component.css']
})
export class PaypalCompleteComponent implements OnInit{

  paymentId: string | null;
  token: string | null;
  payerId: string | null;

  constructor(private route: ActivatedRoute, private axiosService: AxiosService) {
    this.paymentId = null;
    this.token = null;
    this.payerId = null;
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.paymentId = params['paymentId'] || null;
      this.token = params['token'] || null;
      this.payerId = params['PayerID'] || null;


      if (this.paymentId && this.token && this.payerId) {
         console.log("PayerId:" + this.payerId + " payment: " + this.paymentId + " token: " + this.token)

          this.axiosService
            .request('POST', `/paypal/complete/payment?paymentId=${this.paymentId}&token=${this.token}&PayerID=${this.payerId}`, {})
            .then((response: any) => {
              console.log('Payment completion response:', response);
            })
            .catch((error: any) => {
              console.error('Error fetching trip details:', error);
            });
      }
    });
  }
  

}
