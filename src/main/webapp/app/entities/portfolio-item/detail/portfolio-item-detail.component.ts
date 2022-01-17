import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPortfolioItem } from '../portfolio-item.model';

@Component({
  selector: 'jhi-portfolio-item-detail',
  templateUrl: './portfolio-item-detail.component.html',
})
export class PortfolioItemDetailComponent implements OnInit {
  portfolioItem: IPortfolioItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ portfolioItem }) => {
      this.portfolioItem = portfolioItem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
