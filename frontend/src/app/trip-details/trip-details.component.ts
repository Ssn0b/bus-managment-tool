import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TripDTO } from 'src/dto/trip-dto.model';
import { AxiosService } from 'src/services/axios-service/axios.service';


@Component({
  selector: 'app-trip-details',
  templateUrl: './trip-details.component.html',
  styleUrls: ['./trip-details.component.css']
})
export class TripDetailsComponent implements OnInit{
  trip: TripDTO | undefined;
  seats: number[] = [];
  selectedSeats: number[] = [];
  totalPrice: number = 0;
  
  constructor(private route: ActivatedRoute, private axiosService: AxiosService){
    
  }
  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      const tripId = params['id'];
      if (tripId) {
        this.getTripDetails(tripId);
      }
    });  
    
  }
  getTripDetails(tripId: string): void {
    this.axiosService
      .request('GET', `/user/trips/${tripId}`, {})
      .then((response: any) => {
        this.trip = response.data;
        if (this.trip && this.trip.bus.seats) {
          this.seats = Array(this.trip.bus.seats).fill(0).map((_, index) => index);
        }
      })
      .catch((error: any) => {
        console.error('Error fetching trip details:', error);
      });
  }
  redirectToPayPal() {
    if (this.trip && this.trip.ticketPrice) {
    this.axiosService
      .request('POST', `/paypal/make/payment?sum=${this.trip.ticketPrice}`, {})
      .then((response: any) => {
        if (response.data.status === 'success') {
          console.log(response.data.status)
          window.location.href = response.data.redirect_url;
        } else {
          console.error('Payment request failed');
        }
      })
      .catch((error: any) => {
        console.error('Error fetching trip details:', error);
      });
    }
  }
}
