import { Component, OnInit } from '@angular/core';
import { AxiosService } from 'src/services/axios-service/axios.service';
import { RouteDTO } from 'src/dto/route-dto.model';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-route-list',
  templateUrl: './route-list.component.html',
  styleUrls: ['./route-list.component.css']
})
export class RouteListComponent implements OnInit {

  routes: RouteDTO[] = [];
  displayedColumns: string[] = ['routeNumber', 'name', 'distance', 'duration'];
  dataSource: MatTableDataSource<RouteDTO>;

  constructor(private axiosService: AxiosService) {
    this.dataSource = new MatTableDataSource(this.routes);
  }

  ngOnInit() {
    this.getRoutes();
  }

  getRoutes() {
    this.axiosService.request("GET", "/routes", {}).then(
      (response: any) => {
        this.routes = response.data; // Assuming the data is an array of RouteDTO
        this.dataSource.data = this.routes;
      }).catch(
      (error: any) => {
        console.error('Error fetching routes:', error);
      }
    );

    // this.routeService.getRoutes().then((data: RouteDTO[]) => {
    //   this.routes = data;
    //   this.dataSource.data = this.routes;
    // }).catch((error) => {
    //   console.error('Error fetching routes:', error);
    // });
  }
}

