import { Component, OnInit,HostListener } from '@angular/core';
import { InactivityService } from 'src/services/inactivity-service/inactivity.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent{
  title = 'frontend';

  constructor() { }
  
}
