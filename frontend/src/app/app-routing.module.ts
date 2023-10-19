import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './main/home/home.component';
import { RegistrationComponent } from './auth/registration/registration.component';
import { AuthenticationComponent } from './auth/authentication/authentication.component';
import { TripListComponent } from './lists/trip-list/trip-list.component';
import { TripDetailsComponent } from './trip-details/trip-details.component';
import { PaypalCompleteComponent } from './paypal-complete/paypal-complete.component';
import { EmailConfirmationComponent } from './auth/email-confirmation/email-confirmation.component';
import { CityAndStopsComponent } from './city-and-stops/city-and-stops.component';


const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },         
  { path: 'register', component: RegistrationComponent},
  { path: 'authenticate', component: AuthenticationComponent},
  { path: 'city-and-stops', component: CityAndStopsComponent},
  { path: 'trips', component: TripListComponent},
  { path: 'trip/:id', component: TripDetailsComponent },
  // { path: 'city/:id', component: CityDetailsComponent },
  { path: 'complete-payment', component: PaypalCompleteComponent },
  { path: 'confirm', component: EmailConfirmationComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
