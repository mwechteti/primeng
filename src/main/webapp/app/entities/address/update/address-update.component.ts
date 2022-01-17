import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ICountry } from 'app/entities/country/country.model';
import { CountryService } from 'app/entities/country/service/country.service';
import { ILegalEntity } from 'app/entities/legal-entity/legal-entity.model';
import { LegalEntityService } from 'app/entities/legal-entity/service/legal-entity.service';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';
import { Address, IAddress } from '../address.model';
import { AddressService } from '../service/address.service';


@Component({
  selector: 'jhi-address-update',
  templateUrl: './address-update.component.html',
})
export class AddressUpdateComponent implements OnInit {
  isSaving = false;

  countriesCollection: ICountry[] = [];
  legalEntitiesSharedCollection: ILegalEntity[] = [];

  editForm = this.fb.group({
    id: [],
    label: [null, [Validators.required]],
    postCode: [null, [Validators.required]],
    addressLine1: [null, [Validators.required]],
    addressLine2: [],
    city: [null, [Validators.required]],
    main: [null, [Validators.required]],
    country: [],
    legalEntity: [],
  });

  constructor(
    protected addressService: AddressService,
    protected countryService: CountryService,
    protected legalEntityService: LegalEntityService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe((address: IAddress) => {
      // this is necessary to extract the entity from the wrapping object sent by the route resolve
      const entityToUpdate: IAddress = Object(address)["address"];
      this.updateForm(entityToUpdate);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const address = this.createFromForm();
    if (address.id !== undefined) {
      this.subscribeToSaveResponse(this.addressService.update(address));
    } else {
      this.subscribeToSaveResponse(this.addressService.create(address));
    }
  }

  trackCountryById(index: number, item: ICountry): number {
    return item.id!;
  }

  trackLegalEntityById(index: number, item: ILegalEntity): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddress>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError()
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(address: IAddress): void {
    this.editForm.patchValue({
      id: address.id,
      label: address.label,
      postCode: address.postCode,
      addressLine1: address.addressLine1,
      addressLine2: address.addressLine2,
      city: address.city,
      main: address.main,
      country: address.country,
      legalEntity: address.legalEntity,
    });

    this.countriesCollection = this.countryService.addCountryToCollectionIfMissing(this.countriesCollection, address.country);
    this.legalEntitiesSharedCollection = this.legalEntityService.addLegalEntityToCollectionIfMissing(
      this.legalEntitiesSharedCollection,
      address.legalEntity
    );
  }

  protected loadRelationshipsOptions(): void {
    this.countryService
      .query({ filter: 'address-is-null' })
      .pipe(map((res: HttpResponse<ICountry[]>) => res.body ?? []))
      .pipe(
        map((countries: ICountry[]) => this.countryService.addCountryToCollectionIfMissing(countries, this.editForm.get('country')!.value as ICountry))
      )
      .subscribe((countries: ICountry[]) => (this.countriesCollection = countries));

    this.legalEntityService
      .query()
      .pipe(map((res: HttpResponse<ILegalEntity[]>) => res.body ?? []))
      .pipe(
        map((legalEntities: ILegalEntity[]) =>
          this.legalEntityService.addLegalEntityToCollectionIfMissing(legalEntities, this.editForm.get('legalEntity')!.value as ILegalEntity)
        )
      )
      .subscribe((legalEntities: ILegalEntity[]) => (this.legalEntitiesSharedCollection = legalEntities));
  }

  protected createFromForm(): IAddress {
    return {
      ...new Address(),
      id: this.editForm.get(['id'])!.value,
      label: this.editForm.get(['label'])!.value,
      postCode: this.editForm.get(['postCode'])!.value,
      addressLine1: this.editForm.get(['addressLine1'])!.value,
      addressLine2: this.editForm.get(['addressLine2'])!.value,
      city: this.editForm.get(['city'])!.value,
      main: this.editForm.get(['main'])!.value,
      country: this.editForm.get(['country'])!.value,
      legalEntity: this.editForm.get(['legalEntity'])!.value,
    };
  }
}
