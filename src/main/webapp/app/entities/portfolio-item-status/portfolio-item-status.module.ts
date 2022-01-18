import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PortfolioItemStatusRoutingModule } from './route/portfolio-item-status-routing.module';
import { PortfolioItemStatusComponent } from './portfolio-item-status.component';



@NgModule({
  imports: [
    SharedModule, 
    PortfolioItemStatusRoutingModule,
  ],
  declarations: [PortfolioItemStatusComponent],
})
export class PortfolioItemStatusModule { }
