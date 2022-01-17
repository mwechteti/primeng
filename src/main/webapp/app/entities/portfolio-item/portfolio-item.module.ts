import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PortfolioItemComponent } from './list/portfolio-item.component';
import { PortfolioItemDetailComponent } from './detail/portfolio-item-detail.component';
import { PortfolioItemUpdateComponent } from './update/portfolio-item-update.component';
import { PortfolioItemDeleteDialogComponent } from './delete/portfolio-item-delete-dialog.component';
import { PortfolioItemRoutingModule } from './route/portfolio-item-routing.module';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@NgModule({
  imports: [
    SharedModule, 
    PortfolioItemRoutingModule,
    MatButtonModule,
    MatIconModule,],
  declarations: [PortfolioItemComponent, PortfolioItemDetailComponent, PortfolioItemUpdateComponent, PortfolioItemDeleteDialogComponent],
})
export class PortfolioItemModule {}
