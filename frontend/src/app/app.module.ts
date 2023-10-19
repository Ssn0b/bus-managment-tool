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
import { HeaderComponent } from './main/header/header.component';
import { HomeComponent } from './main/home/home.component';
import { PhotoCarouselComponent } from './main/photo-carousel/photo-carousel.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RegistrationComponent } from './auth/registration/registration.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AuthenticationComponent } from './auth/authentication/authentication.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { RouteListComponent } from './main/route-list/route-list.component';
import { MatTableModule } from '@angular/material/table';
import { MatGridListModule } from '@angular/material/grid-list';

import { InactivityService } from 'src/services/inactivity-service/inactivity.service';
import { TripListComponent } from './lists/trip-list/trip-list.component';
import { TripDetailsComponent } from './trip-details/trip-details.component';
import { PaypalCompleteComponent } from './paypal-complete/paypal-complete.component';
import { EmailConfirmationComponent } from 'src/app/auth/email-confirmation/email-confirmation.component';
import { PopupDialogComponent } from './popup-dialog/popup-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { CityAndStopsComponent } from './city-and-stops/city-and-stops.component';
import { MatInputModule } from '@angular/material/input';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    PhotoCarouselComponent,
    HeaderComponent,
    RegistrationComponent,
    AuthenticationComponent,
    RouteListComponent,
    TripListComponent,
    TripDetailsComponent,
    PaypalCompleteComponent,
    EmailConfirmationComponent,
    PopupDialogComponent,
    CityAndStopsComponent,
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
    MatGridListModule,
    MatDialogModule,
    MatInputModule ,
  ],
  providers: [InactivityService],
  bootstrap: [AppComponent]
})
export class AppModule { }
