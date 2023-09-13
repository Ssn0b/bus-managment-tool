import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './main/home/home.component';
import { RegistrationComponent } from './auth/registration/registration.component';
import { AuthenticationComponent } from './auth/authentication/authentication.component';


const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' }, // Redirect empty path to '/home'
  { path: 'home', component: HomeComponent },         // Home component route
  { path: 'register', component: RegistrationComponent},
  { path: 'authenticate', component: AuthenticationComponent},
  // { path: 'manage', component: ManageBusesComponent, canActivate: [AuthGuard], data: { requiredRole: 'MANAGER' } },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
