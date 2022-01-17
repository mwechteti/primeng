import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DashboardComponent } from './dashboard.component';

import { DashboardRoutingModule } from './route/dashboard-routing.module';

@NgModule({
  imports: [SharedModule, DashboardRoutingModule],
  declarations: [DashboardComponent],
})
export class DashboardModule {}
