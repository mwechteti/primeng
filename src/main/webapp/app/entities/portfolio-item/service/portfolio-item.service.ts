import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPortfolioItem, getPortfolioItemIdentifier } from '../portfolio-item.model';

export type EntityResponseType = HttpResponse<IPortfolioItem>;
export type EntityArrayResponseType = HttpResponse<IPortfolioItem[]>;

@Injectable({ providedIn: 'root' })
export class PortfolioItemService {
  protected resourceUrlSingle = this.applicationConfigService.getEndpointFor('api/portfolio-item');
  protected resourceUrlMultiple = this.applicationConfigService.getEndpointFor('api/portfolio-items');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(portfolioItem: IPortfolioItem): Observable<EntityResponseType> {
    return this.http.post<IPortfolioItem>(this.resourceUrlMultiple, portfolioItem, { observe: 'response' });
  }

  update(portfolioItem: IPortfolioItem): Observable<EntityResponseType> {
    return this.http.put<IPortfolioItem>(`${this.resourceUrlMultiple}/${getPortfolioItemIdentifier(portfolioItem) as number}`, portfolioItem, {
      observe: 'response',
    });
  }

  partialUpdate(portfolioItem: IPortfolioItem): Observable<EntityResponseType> {
    return this.http.patch<IPortfolioItem>(`${this.resourceUrlMultiple}/${getPortfolioItemIdentifier(portfolioItem) as number}`, portfolioItem, {
      observe: 'response',
    });
  }

  findById(id: number): Observable<EntityResponseType> {
    return this.http.get<IPortfolioItem>(`${this.resourceUrlSingle}/${id}`, { observe: 'response' });
  }

  findByPortfolioId(portfolioId: number): Observable<IPortfolioItem[]> {
    return this.http.get<IPortfolioItem[]>(`${this.resourceUrlMultiple}/${portfolioId}`);
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPortfolioItem[]>(this.resourceUrlMultiple, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrlMultiple}/${id}`, { observe: 'response' });
  }

  addPortfolioItemToCollectionIfMissing(
    portfolioItemCollection: IPortfolioItem[],
    ...portfolioItemsToCheck: (IPortfolioItem | null | undefined)[]
  ): IPortfolioItem[] {
    const portfolioItems: IPortfolioItem[] = portfolioItemsToCheck.filter(isPresent);
    if (portfolioItems.length > 0) {
      const portfolioItemCollectionIdentifiers = portfolioItemCollection.map(
        portfolioItemItem => getPortfolioItemIdentifier(portfolioItemItem)!
      );
      const portfolioItemsToAdd = portfolioItems.filter(portfolioItemItem => {
        const portfolioItemIdentifier = getPortfolioItemIdentifier(portfolioItemItem);
        if (portfolioItemIdentifier == null || portfolioItemCollectionIdentifiers.includes(portfolioItemIdentifier)) {
          return false;
        }
        portfolioItemCollectionIdentifiers.push(portfolioItemIdentifier);
        return true;
      });
      return [...portfolioItemsToAdd, ...portfolioItemCollection];
    }
    return portfolioItemCollection;
  }
}
