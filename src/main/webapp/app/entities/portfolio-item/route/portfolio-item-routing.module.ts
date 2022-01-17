import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PortfolioItemComponent } from '../list/portfolio-item.component';
import { PortfolioItemDetailComponent } from '../detail/portfolio-item-detail.component';
import { PortfolioItemUpdateComponent } from '../update/portfolio-item-update.component';
import { PortfolioItemRoutingResolveService } from './portfolio-item-routing-resolve.service';

const portfolioItemRoute: Routes = [
  {
    path: '',
    component: PortfolioItemComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PortfolioItemDetailComponent,
    resolve: {
      portfolioItem: PortfolioItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PortfolioItemUpdateComponent,
    resolve: {
      portfolioItem: PortfolioItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PortfolioItemUpdateComponent,
    resolve: {
      portfolioItem: PortfolioItemRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(portfolioItemRoute)],
  exports: [RouterModule],
})
export class PortfolioItemRoutingModule {}
