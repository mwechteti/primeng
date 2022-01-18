import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { ApplicationConfigService } from '../../../core/config/application-config.service';
import { createRequestOption } from '../../../core/request/request-util';
import { IPortfolioItemStatus } from '../portfolio-item-status.model';


export type EntityResponseType = HttpResponse<IPortfolioItemStatus>;
export type EntityArrayResponseType = HttpResponse<IPortfolioItemStatus[]>;

@Injectable({
  providedIn: 'root'
})
export class PortfolioItemStatusService {

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/portfolio-item-status');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPortfolioItemStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  findAll(): Observable<IPortfolioItemStatus[]> {
    return this.http.get<IPortfolioItemStatus[]>(`${this.resourceUrl}`);
  }

}
