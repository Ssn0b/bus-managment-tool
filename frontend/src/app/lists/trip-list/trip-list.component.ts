import { Component, OnInit } from '@angular/core';
import { AxiosService } from 'src/services/axios-service/axios.service';
import { TripDTO } from 'src/dto/trip-dto.model';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-trip-list',
  templateUrl: './trip-list.component.html',
  styleUrls: ['./trip-list.component.css']
})
export class TripListComponent implements OnInit {

  trips: TripDTO[] = [];
  displayedColumns: string[] = [
    'tripNumber',
    'departureTime',
    'arrivalTime',
    'startStop', 
    'finishStop', 
    'ticketPrice',
    'actions'
  ];  
  dataSource: MatTableDataSource<TripDTO>;

  constructor(private axiosService: AxiosService) {
    this.dataSource = new MatTableDataSource(this.trips);
  }

  ngOnInit() {
    this.getTrips();
  }

  async getTrips() {
    try {
      const response = await this.axiosService.request("GET", "/user/trips", {});
      this.trips = response.data;
      this.dataSource.data = this.trips;
    } catch (error) {
      console.error('Error fetching trips:', error);
    }
  }

  
}
