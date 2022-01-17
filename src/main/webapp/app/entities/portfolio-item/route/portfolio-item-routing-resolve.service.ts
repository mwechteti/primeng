import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPortfolioItem, PortfolioItem } from '../portfolio-item.model';
import { PortfolioItemService } from '../service/portfolio-item.service';

@Injectable({ providedIn: 'root' })
export class PortfolioItemRoutingResolveService implements Resolve<IPortfolioItem> {
  constructor(protected service: PortfolioItemService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPortfolioItem> | Observable<never> {
    const id: number = route.params['id'];
    if (id) {
      return this.service.findById(id).pipe(
        mergeMap((portfolioItem: HttpResponse<PortfolioItem>) => {
          if (portfolioItem.body) {
            return of(portfolioItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PortfolioItem());
  }
}
