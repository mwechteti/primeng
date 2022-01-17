import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PortfolioDetailComponent } from '../detail/portfolio-detail.component';
import { PortfolioComponent } from '../list/portfolio.component';
import { PortfolioUpdateComponent } from '../update/portfolio-update.component';
import { PortfolioRoutingResolveService } from './portfolio-routing-resolve.service';


const portfolioRoute: Routes = [
  {
    path: '',
    component: PortfolioComponent,
  },
  {
    path: ':id/view',
    component: PortfolioDetailComponent,
    resolve: {
      address: PortfolioRoutingResolveService,
    },
  },
  {
    path: 'new',
    component: PortfolioUpdateComponent,
    resolve: {
      address: PortfolioRoutingResolveService,
    },
  },
  {
    path: ':id/edit',
    component: PortfolioUpdateComponent,
    resolve: {
      address: PortfolioRoutingResolveService,
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(portfolioRoute)],
  exports: [RouterModule],
})
export class PortfolioRoutingModule {}
