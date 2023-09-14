import { Component, OnInit } from '@angular/core';
import { RouteService } from 'src/services/route-service/route.service';
import { RouteDTO } from 'src/dto/route-dto.model'; 
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-route-list',
  templateUrl: './route-list.component.html',
  styleUrls: ['./route-list.component.css']
})
export class RouteListComponent implements OnInit{

    routes: RouteDTO[] = [];
    displayedColumns: string[] = ['routeNumber', 'name', 'distance', 'duration', 'actions'];
    dataSource: MatTableDataSource<RouteDTO>;
  
    constructor(private routeService: RouteService) {
      this.dataSource = new MatTableDataSource(this.routes);
    }
  
    ngOnInit() {
      this.getRoutes();
    }
  
    getRoutes() {
      this.routeService.getRoutes().subscribe((data: RouteDTO[]) => {
        this.routes = data;
        this.dataSource.data = this.routes;
      });
    }
 
}
