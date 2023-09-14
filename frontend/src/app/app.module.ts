import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { NgImageSliderModule } from 'ng-image-slider';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './main/navbar/navbar.component';
import { HeaderComponent } from './main/header/header.component';
import { HomeComponent } from './main/home/home.component';
import { PhotoCarouselComponent } from './photo-carousel/photo-carousel.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RegistrationComponent } from './auth/registration/registration.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AuthenticationComponent } from './auth/authentication/authentication.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { RouteListComponent } from './route-list/route-list.component';
import { MatTableModule } from '@angular/material/table';

import { InactivityService } from 'src/services/inactivity-service/inactivity.service';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavbarComponent,
    PhotoCarouselComponent,
    HeaderComponent,
    RegistrationComponent,
    AuthenticationComponent,
    RouteListComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatMenuModule,
    NgImageSliderModule,
    AppRoutingModule,
    CommonModule,
    FormsModule,
    BrowserModule, 
    AppRoutingModule, 
    ReactiveFormsModule,
    MatSidenavModule,
    HttpClientModule,
    MatTableModule,
  ],
  providers: [InactivityService],
  bootstrap: [AppComponent]
})
export class AppModule { }
