jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPortfolioItem, PortfolioItem } from '../portfolio-item.model';
import { PortfolioItemService } from '../service/portfolio-item.service';

import { PortfolioItemRoutingResolveService } from './portfolio-item-routing-resolve.service';

describe('PortfolioItem routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PortfolioItemRoutingResolveService;
  let service: PortfolioItemService;
  let resultPortfolioItem: IPortfolioItem | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(PortfolioItemRoutingResolveService);
    service = TestBed.inject(PortfolioItemService);
    resultPortfolioItem = undefined;
  });

  describe('resolve', () => {
    it('should return IPortfolioItem returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPortfolioItem = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPortfolioItem).toEqual({ id: 123 });
    });

    it('should return new IPortfolioItem if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPortfolioItem = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPortfolioItem).toEqual(new PortfolioItem());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PortfolioItem })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPortfolioItem = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPortfolioItem).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
