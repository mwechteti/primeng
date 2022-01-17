import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { IDashboard } from '../dashboard.model';
import { Observable } from 'rxjs';
import { createRequestOption } from 'app/core/request/request-util';
import { ApplicationConfigService } from 'app/core/config/application-config.service';

export type EntityResponseType = HttpResponse<IDashboard>;
export type EntityArrayResponseType = HttpResponse<IDashboard[]>;

@Injectable({ providedIn: 'root' })
@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('dashboard');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDashboard[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}
