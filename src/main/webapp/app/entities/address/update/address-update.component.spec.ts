jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AddressService } from '../service/address.service';
import { IAddress, Address } from '../address.model';
import { ICountry } from 'app/entities/country/country.model';
import { CountryService } from 'app/entities/country/service/country.service';
import { ILegalEntity } from 'app/entities/legal-entity/legal-entity.model';
import { LegalEntityService } from 'app/entities/legal-entity/service/legal-entity.service';

import { AddressUpdateComponent } from './address-update.component';

describe('Address Management Update Component', () => {
  let comp: AddressUpdateComponent;
  let fixture: ComponentFixture<AddressUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let addressService: AddressService;
  let countryService: CountryService;
  let legalEntityService: LegalEntityService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AddressUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(AddressUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AddressUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    addressService = TestBed.inject(AddressService);
    countryService = TestBed.inject(CountryService);
    legalEntityService = TestBed.inject(LegalEntityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call country query and add missing value', () => {
      const address: IAddress = { id: 456 };
      const country: ICountry = { id: 6792 };
      address.country = country;

      const countryCollection: ICountry[] = [{ id: 9922 }];
      jest.spyOn(countryService, 'query').mockReturnValue(of(new HttpResponse({ body: countryCollection })));
      const expectedCollection: ICountry[] = [country, ...countryCollection];
      jest.spyOn(countryService, 'addCountryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ address });
      comp.ngOnInit();

      expect(countryService.query).toHaveBeenCalled();
      expect(countryService.addCountryToCollectionIfMissing).toHaveBeenCalledWith(countryCollection, country);
      expect(comp.countriesCollection).toEqual(expectedCollection);
    });

    it('Should call LegalEntity query and add missing value', () => {
      const address: IAddress = { id: 456 };
      const legalEntity: ILegalEntity = { id: 94085 };
      address.legalEntity = legalEntity;

      const legalEntityCollection: ILegalEntity[] = [{ id: 2071 }];
      jest.spyOn(legalEntityService, 'query').mockReturnValue(of(new HttpResponse({ body: legalEntityCollection })));
      const additionalLegalEntities = [legalEntity];
      const expectedCollection: ILegalEntity[] = [...additionalLegalEntities, ...legalEntityCollection];
      jest.spyOn(legalEntityService, 'addLegalEntityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ address });
      comp.ngOnInit();

      expect(legalEntityService.query).toHaveBeenCalled();
      expect(legalEntityService.addLegalEntityToCollectionIfMissing).toHaveBeenCalledWith(
        legalEntityCollection,
        ...additionalLegalEntities
      );
      expect(comp.legalEntitiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const address: IAddress = { id: 456 };
      const country: ICountry = { id: 11248 };
      address.country = country;
      const legalEntity: ILegalEntity = { id: 92403 };
      address.legalEntity = legalEntity;

      activatedRoute.data = of({ address });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(address));
      expect(comp.countriesCollection).toContain(country);
      expect(comp.legalEntitiesSharedCollection).toContain(legalEntity);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Address>>();
      const address = { id: 123 };
      jest.spyOn(addressService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ address });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: address }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(addressService.update).toHaveBeenCalledWith(address);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Address>>();
      const address = new Address();
      jest.spyOn(addressService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ address });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: address }));
      saveSubject.complete();

      // THEN
      expect(addressService.create).toHaveBeenCalledWith(address);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Address>>();
      const address = { id: 123 };
      jest.spyOn(addressService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ address });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(addressService.update).toHaveBeenCalledWith(address);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCountryById', () => {
      it('Should return tracked Country primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCountryById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackLegalEntityById', () => {
      it('Should return tracked LegalEntity primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackLegalEntityById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
