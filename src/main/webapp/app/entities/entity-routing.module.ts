import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'legal-entity',
        data: { pageTitle: 'rmkproApp.legalEntity.home.title' },
        loadChildren: () => import('./legal-entity/legal-entity.module').then(m => m.LegalEntityModule),
      },
      {
        path: 'model',
        data: { pageTitle: 'rmkproApp.model.home.title' },
        loadChildren: () => import('./model/model.module').then(m => m.ModelModule),
      },
      {
        path: 'make',
        data: { pageTitle: 'rmkproApp.make.home.title' },
        loadChildren: () => import('./make/make.module').then(m => m.MakeModule),
      },
      {
        path: 'vehicle',
        data: { pageTitle: 'rmkproApp.vehicle.home.title' },
        loadChildren: () => import('./vehicle/vehicle.module').then(m => m.VehicleModule),
      },
      {
        path: 'legal-entity-type',
        data: { pageTitle: 'rmkproApp.legalEntityType.home.title' },
        loadChildren: () => import('./legal-entity-type/legal-entity-type.module').then(m => m.LegalEntityTypeModule),
      },
      {
        path: 'address',
        data: { pageTitle: 'rmkproApp.address.home.title' },
        loadChildren: () => import('./address/address.module').then(m => m.AddressModule),
      },
      {
        path: 'country',
        data: { pageTitle: 'rmkproApp.country.home.title' },
        loadChildren: () => import('./country/country.module').then(m => m.CountryModule),
      },
      {
        path: 'portfolio',
        data: { pageTitle: 'rmkproApp.portfolio.home.title' },
        loadChildren: () => import('./portfolio/portfolio.module').then(m => m.PortfolioModule),
      },
      {
        path: 'portfolio-item',
        data: { pageTitle: 'rmkproApp.portfolioItem.home.title' },
        loadChildren: () => import('./portfolio-item/portfolio-item.module').then(m => m.PortfolioItemModule),
      },
      {
        path: 'portfolio-item-status',
        data: { pageTitle: 'rmkproApp.portfolioItemStatus.home.title' },
        loadChildren: () => import('./portfolio-item-status/portfolio-item-status.module').then(m => m.PortfolioItemStatusModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
