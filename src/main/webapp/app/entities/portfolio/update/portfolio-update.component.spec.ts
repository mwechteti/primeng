jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { PortfolioService } from '../service/portfolio.service';
import { IPortfolio, Portfolio } from '../portfolio.model';

import { PortfolioUpdateComponent } from './portfolio-update.component';

describe('Portfolio Management Update Component', () => {
  let comp: PortfolioUpdateComponent;
  let fixture: ComponentFixture<PortfolioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let portfolioService: PortfolioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PortfolioUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(PortfolioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PortfolioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    portfolioService = TestBed.inject(PortfolioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const portfolio: IPortfolio = { id: 456 };

      activatedRoute.data = of({ portfolio });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(portfolio));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Portfolio>>();
      const portfolio = { id: 123 };
      jest.spyOn(portfolioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ portfolio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: portfolio }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(portfolioService.update).toHaveBeenCalledWith(portfolio);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Portfolio>>();
      const portfolio = new Portfolio();
      jest.spyOn(portfolioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ portfolio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: portfolio }));
      saveSubject.complete();

      // THEN
      expect(portfolioService.create).toHaveBeenCalledWith(portfolio);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Portfolio>>();
      const portfolio = { id: 123 };
      jest.spyOn(portfolioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ portfolio });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(portfolioService.update).toHaveBeenCalledWith(portfolio);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
