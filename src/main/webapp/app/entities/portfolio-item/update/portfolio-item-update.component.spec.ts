jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PortfolioItemService } from '../service/portfolio-item.service';
import { IPortfolioItem, PortfolioItem } from '../portfolio-item.model';
import { IVehicle } from 'app/entities/vehicle/vehicle.model';
import { VehicleService } from 'app/entities/vehicle/service/vehicle.service';
import { IPortfolioItemDetails } from 'app/entities/portfolio-item-details/portfolio-item-details.model';
import { PortfolioItemDetailsService } from 'app/entities/portfolio-item-details/service/portfolio-item-details.service';
import { IPortfolio } from 'app/entities/portfolio/portfolio.model';
import { PortfolioService } from 'app/entities/portfolio/service/portfolio.service';

import { PortfolioItemUpdateComponent } from './portfolio-item-update.component';

describe('PortfolioItem Management Update Component', () => {
  let comp: PortfolioItemUpdateComponent;
  let fixture: ComponentFixture<PortfolioItemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let portfolioItemService: PortfolioItemService;
  let vehicleService: VehicleService;
  let portfolioItemDetailsService: PortfolioItemDetailsService;
  let portfolioService: PortfolioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PortfolioItemUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(PortfolioItemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PortfolioItemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    portfolioItemService = TestBed.inject(PortfolioItemService);
    vehicleService = TestBed.inject(VehicleService);
    portfolioItemDetailsService = TestBed.inject(PortfolioItemDetailsService);
    portfolioService = TestBed.inject(PortfolioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call vehicle query and add missing value', () => {
      const portfolioItem: IPortfolioItem = { id: 456 };
      const vehicle: IVehicle = { id: 54103 };
      portfolioItem.vehicle = vehicle;

      const vehicleCollection: IVehicle[] = [{ id: 93884 }];
      jest.spyOn(vehicleService, 'query').mockReturnValue(of(new HttpResponse({ body: vehicleCollection })));
      const expectedCollection: IVehicle[] = [vehicle, ...vehicleCollection];
      jest.spyOn(vehicleService, 'addVehicleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ portfolioItem });
      comp.ngOnInit();

      expect(vehicleService.query).toHaveBeenCalled();
      expect(vehicleService.addVehicleToCollectionIfMissing).toHaveBeenCalledWith(vehicleCollection, vehicle);
      expect(comp.vehiclesCollection).toEqual(expectedCollection);
    });

    it('Should call portfolioItemDetails query and add missing value', () => {
      const portfolioItem: IPortfolioItem = { id: 456 };
      const portfolioItemDetails: IPortfolioItemDetails = { id: 83116 };
      portfolioItem.portfolioItemDetails = portfolioItemDetails;

      const portfolioItemDetailsCollection: IPortfolioItemDetails[] = [{ id: 2331 }];
      jest
        .spyOn(portfolioItemDetailsService, 'query')
        .mockReturnValue(of(new HttpResponse({ body: portfolioItemDetailsCollection })));
      const expectedCollection: IPortfolioItemDetails[] = [
        portfolioItemDetails,
        ...portfolioItemDetailsCollection,
      ];
      jest
        .spyOn(portfolioItemDetailsService, 'addPortfolioItemDetailsToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ portfolioItem });
      comp.ngOnInit();

      expect(portfolioItemDetailsService.query).toHaveBeenCalled();
      expect(portfolioItemDetailsService.addPortfolioItemDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        portfolioItemDetailsCollection,
        portfolioItemDetails
      );
      expect(comp.portfolioItemDetailsCollection).toEqual(expectedCollection);
    });

    it('Should call Portfolio query and add missing value', () => {
      const portfolioItem: IPortfolioItem = { id: 456 };
      const portfolio: IPortfolio = { id: 29326 };
      portfolioItem.portfolio = portfolio;

      const portfolioCollection: IPortfolio[] = [{ id: 12907 }];
      jest.spyOn(portfolioService, 'query').mockReturnValue(of(new HttpResponse({ body: portfolioCollection })));
      const additionalPortfolios = [portfolio];
      const expectedCollection: IPortfolio[] = [...additionalPortfolios, ...portfolioCollection];
      jest.spyOn(portfolioService, 'addPortfolioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ portfolioItem });
      comp.ngOnInit();

      expect(portfolioService.query).toHaveBeenCalled();
      expect(portfolioService.addPortfolioToCollectionIfMissing).toHaveBeenCalledWith(portfolioCollection, ...additionalPortfolios);
      expect(comp.portfoliosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const portfolioItem: IPortfolioItem = { id: 456 };
      const vehicle: IVehicle = { id: 44023 };
      portfolioItem.vehicle = vehicle;
      const portfolioItemDetails: IPortfolioItemDetails = { id: 73895 };
      portfolioItem.portfolioItemDetails = portfolioItemDetails;
      const portfolio: IPortfolio = { id: 99510 };
      portfolioItem.portfolio = portfolio;

      activatedRoute.data = of({ portfolioItem });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(portfolioItem));
      expect(comp.vehiclesCollection).toContain(vehicle);
      expect(comp.portfolioItemDetailsCollection).toContain(portfolioItemDetails);
      expect(comp.portfoliosSharedCollection).toContain(portfolio);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PortfolioItem>>();
      const portfolioItem = { id: 123 };
      jest.spyOn(portfolioItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ portfolioItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: portfolioItem }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(portfolioItemService.update).toHaveBeenCalledWith(portfolioItem);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PortfolioItem>>();
      const portfolioItem = new PortfolioItem();
      jest.spyOn(portfolioItemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ portfolioItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: portfolioItem }));
      saveSubject.complete();

      // THEN
      expect(portfolioItemService.create).toHaveBeenCalledWith(portfolioItem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<PortfolioItem>>();
      const portfolioItem = { id: 123 };
      jest.spyOn(portfolioItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ portfolioItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(portfolioItemService.update).toHaveBeenCalledWith(portfolioItem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackVehicleById', () => {
      it('Should return tracked Vehicle primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackVehicleById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPortfolioItemDetailsById', () => {
      it('Should return tracked PortfolioItemDetails primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPortfolioItemDetailsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackPortfolioById', () => {
      it('Should return tracked Portfolio primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackPortfolioById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
