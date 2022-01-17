import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PortfolioItemStatusService } from 'app/portfolio-item-status.service';
import { IPortfolioItemStatus } from './portfolio-item-status.model';

@Component({
  selector: 'jhi-portfolio-item-status',
  templateUrl: './portfolio-item-status.component.html',
  styleUrls: ['./portfolio-item-status.component.scss']
})
export class PortfolioItemStatusComponent implements OnInit {
  portfolioItemStatuses?: IPortfolioItemStatus[];
  isLoading = false;

  constructor(protected portfolioItemStatusService: PortfolioItemStatusService, protected modalService: NgbModal) { }

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.isLoading = true;

    this.portfolioItemStatusService.query().subscribe(
      (res: HttpResponse<IPortfolioItemStatus[]>) => {
        this.isLoading = false;
        this.portfolioItemStatuses = res.body ?? [];
      }
    );
  }

}
