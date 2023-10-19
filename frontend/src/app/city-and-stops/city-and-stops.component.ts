import { Component, ElementRef, OnInit,Renderer2, ViewChild  } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatMenuTrigger } from '@angular/material/menu';
import { Router } from '@angular/router';
import * as L from 'leaflet';
import { AxiosService } from 'src/services/axios-service/axios.service';
import { PopupDialogComponent } from 'src/app/popup-dialog/popup-dialog.component';
import { CityDTO } from 'src/dto/city-dto.model';

@Component({
  selector: 'app-city-and-stops',
  templateUrl: './city-and-stops.component.html',
  styleUrls: ['./city-and-stops.component.css']
})
export class CityAndStopsComponent implements OnInit {
  map: any;
  newCity : CityDTO = { id: 0, name: '', latitude: 0, longitude: 0 };
  isMenuOpen = false;

  @ViewChild('menu') menu: MatMenuTrigger | undefined;

  constructor(private axiosService: AxiosService, private dialog: MatDialog, private renderer: Renderer2, private el: ElementRef, private router: Router) { }

  ngOnInit() {
    this.initMap();
    this.loadCitiesAndAddMarkers();
  }

  initMap() {
    this.map = L.map('map').setView([49.8397, 31.1656], 6); // Центрований на Україні
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(this.map);
  }

  loadCitiesAndAddMarkers() {
    this.axiosService.request('GET', 'admin/cities', null)
      .then(response => {
        const cities = response.data;
        if (cities && Array.isArray(cities)) {
          cities.forEach(city => {
            const cityIcon = L.icon({
              iconUrl: 'assets/bus-icon.png',
              iconSize: [30, 30],
            });
            console.log(city.latitude + " " + city.longitude);
            const marker = L.marker([city.latitude, city.longitude], { icon: cityIcon }).addTo(this.map);

            marker.on('click', () => {
              window.location.href = `http://localhost:4200/${city.name.toLowerCase()}`;
            });
          });
        }
      })
      .catch(error => {
        console.error('Помилка при завантаженні міст: ' + error);
      });
  }
  addCity() {
    this.axiosService
      .request('POST', '/admin/cities', this.newCity)
      .then(response => {
        this.openPopupDialog('City adding is successful', 'Continue managing routes!');
        console.log('City added:', this.newCity);
      })
      .catch(error => {
        this.openPopupDialog('Adding city failed', 'Please try again.');
      });
  }

  // Додайте обробник подій для кнопки "Delete City"
  deleteCity() {
    // Тут ви можете реалізувати дії для видалення міста
    // Наприклад, відкрити діалогове вікно підтвердження видалення.
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