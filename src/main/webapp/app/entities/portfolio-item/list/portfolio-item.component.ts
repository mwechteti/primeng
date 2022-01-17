import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPortfolioItem } from '../portfolio-item.model';
import { PortfolioItemService } from '../service/portfolio-item.service';
import { PortfolioItemDeleteDialogComponent } from '../delete/portfolio-item-delete-dialog.component';

@Component({
  selector: 'jhi-portfolio-item',
  templateUrl: './portfolio-item.component.html',
})
export class PortfolioItemComponent implements OnInit {
  portfolioItems?: IPortfolioItem[];
  isLoading = false;

  constructor(protected portfolioItemService: PortfolioItemService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.portfolioItemService.query().subscribe(
      (res: HttpResponse<IPortfolioItem[]>) => {
        this.isLoading = false;
        this.portfolioItems = res.body ?? [];
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPortfolioItem): number {
    return item.id!;
  }

  delete(portfolioItem: IPortfolioItem): void {
    const modalRef = this.modalService.open(PortfolioItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.portfolioItem = portfolioItem;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
