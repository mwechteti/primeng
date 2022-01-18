import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PortfolioItemStatusComponent } from '../portfolio-item-status.component';


const portfolioItemStatusRoute: Routes = [
  {
    path: 'portfolio-item-status',
    component: PortfolioItemStatusComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(portfolioItemStatusRoute)],
  exports: [RouterModule],
})
export class PortfolioItemStatusRoutingModule {}
