import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPortfolioItem } from '../portfolio-item.model';
import { PortfolioItemService } from '../service/portfolio-item.service';

@Component({
  templateUrl: './portfolio-item-delete-dialog.component.html',
})
export class PortfolioItemDeleteDialogComponent {
  portfolioItem?: IPortfolioItem;

  constructor(protected portfolioItemService: PortfolioItemService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.portfolioItemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
