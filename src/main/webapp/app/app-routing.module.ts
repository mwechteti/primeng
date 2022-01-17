import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LegalEntityComponent } from './entities/legal-entity/list/legal-entity.component';
import { PortfolioComponent } from './entities/portfolio/list/portfolio.component';
import { VehicleComponent } from './entities/vehicle/list/vehicle.component';
import { HomeComponent } from './home/home.component';
import { AlertComponent } from './shared/alert/alert.component';

export const routes: Routes = [
  { path: '', component: HomeComponent}, // this view will show the portfolio content with all the vehicles
  { path: 'dashboard', component: DashboardComponent}, // this view will show the dashboard with graphs
  { path: 'portfolio', component: PortfolioComponent}, // this view will show the Portfolio Management (adding and removing portfolio items)
  { path: 'vehicle', component: VehicleComponent}, // this view will show the list of all existing vehicles
  { path: 'legal-entity', component: LegalEntityComponent}, // this view will show the list of all legal entities
  { path: 'notification', component: AlertComponent}, // this view will show all the alerts
];

@NgModule({
  exports: [RouterModule] // make this module accessible from other modules
})
export class AppRoutingModule { }

