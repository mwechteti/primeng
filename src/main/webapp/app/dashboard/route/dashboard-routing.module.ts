import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { DashboardComponent } from '../dashboard.component';
import { DashboardRoutingResolveService } from './dashboard-routing-resolve.service';

const dashboardRoute: Routes = [
  {
    path: 'dashboard',
    component: DashboardComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(dashboardRoute)],
  exports: [RouterModule],
})
export class DashboardRoutingModule {}
