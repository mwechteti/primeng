import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { ILegalEntity } from 'app/entities/legal-entity/legal-entity.model';
import { LegalEntityService } from 'app/entities/legal-entity/service/legal-entity.service';
import { IMake } from 'app/entities/make/make.model';
import { MakeService } from 'app/entities/make/service/make.service';
import { IModel } from 'app/entities/model/model.model';
import { ModelService } from 'app/entities/model/service/model.service';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';
import { HttpVehicleService } from '../service/vehicle.service';
import { IVehicle, Vehicle } from '../vehicle.model';


@Component({
  selector: 'jhi-vehicle-update',
  templateUrl: './vehicle-update.component.html',
})
export class VehicleUpdateComponent implements OnInit {
  isSaving = false;

  makesSharedCollection: IMake[] = [];
  modelsSharedCollection: IModel[] = [];
  legalEntitiesSharedCollection: ILegalEntity[] = [];

  editForm = this.fb.group({
    id: [],
    plateNumber: [null, [Validators.required]],
    firstRegistrationDate: [null, [Validators.required]],
    used: [null, [Validators.required]],
    mileage: [null, [Validators.required]],
    mileageUnit: [null, [Validators.required]],
    fuelType: [null, [Validators.required]],
    vin: [],
    make: [],
    model: [],
    owner: [],
  });

  constructor(
    protected vehicleService: HttpVehicleService,
    protected makeService: MakeService,
    protected modelService: ModelService,
    protected legalEntityService: LegalEntityService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe((vehicle: IVehicle) => {
      // this is necessary to extract the entity from the wrapping object sent by the route resolve
      const entityToUpdate: IVehicle = Object(vehicle)["vehicle"];
      this.updateForm(entityToUpdate);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vehicle = this.createFromForm();
    if (vehicle.id !== undefined) {
      this.subscribeToSaveResponse(this.vehicleService.update(vehicle));
    } else {
      this.subscribeToSaveResponse(this.vehicleService.create(vehicle));
    }
  }

  trackMakeById(index: number, item: IMake): number {
    return item.id!;
  }

  trackModelById(index: number, item: IModel): number {
    return item.id!;
  }

  trackLegalEntityById(index: number, item: ILegalEntity): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVehicle>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
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

  protected updateForm(vehicle: IVehicle): void {
    this.editForm.patchValue({
      id: vehicle.id,
      plateNumber: vehicle.plateNumber,
      firstRegistrationDate: vehicle.firstRegistrationDate,
      used: vehicle.used,
      mileage: vehicle.mileage,
      mileageUnit: vehicle.mileageUnit,
      fuelType: vehicle.fuelType,
      vin: vehicle.vin,
      make: vehicle.make,
      model: vehicle.model,
      owner: vehicle.owner,
    });

    this.makesSharedCollection = this.makeService.addMakeToCollectionIfMissing(this.makesSharedCollection, vehicle.make);
    this.modelsSharedCollection = this.modelService.addModelToCollectionIfMissing(this.modelsSharedCollection, vehicle.model);
    this.legalEntitiesSharedCollection = this.legalEntityService.addLegalEntityToCollectionIfMissing(
      this.legalEntitiesSharedCollection,
      vehicle.owner
    );
  }

  protected loadRelationshipsOptions(): void {
    this.makeService
      .query()
      .pipe(map((res: HttpResponse<IMake[]>) => res.body ?? []))
      .pipe(map((makes: IMake[]) => this.makeService.addMakeToCollectionIfMissing(makes, this.editForm.get('make')!.value as IMake)))
      .subscribe((makes: IMake[]) => (this.makesSharedCollection = makes));

    this.modelService
      .query()
      .pipe(map((res: HttpResponse<IModel[]>) => res.body ?? []))
      .pipe(map((models: IModel[]) => this.modelService.addModelToCollectionIfMissing(models, this.editForm.get('model')!.value as IModel)))
      .subscribe((models: IModel[]) => (this.modelsSharedCollection = models));

    this.legalEntityService
      .query()
      .pipe(map((res: HttpResponse<ILegalEntity[]>) => res.body ?? []))
      .pipe(
        map((legalEntities: ILegalEntity[]) =>
          this.legalEntityService.addLegalEntityToCollectionIfMissing(legalEntities, this.editForm.get('owner')!.value as ILegalEntity)
        )
      )
      .subscribe((legalEntities: ILegalEntity[]) => (this.legalEntitiesSharedCollection = legalEntities));
  }

  protected createFromForm(): IVehicle {
    return {
      ...new Vehicle(),
      id: this.editForm.get(['id'])!.value,
      plateNumber: this.editForm.get(['plateNumber'])!.value,
      firstRegistrationDate: this.editForm.get(['firstRegistrationDate'])!.value,
      used: this.editForm.get(['used'])!.value,
      mileage: this.editForm.get(['mileage'])!.value,
      mileageUnit: this.editForm.get(['mileageUnit'])!.value,
      fuelType: this.editForm.get(['fuelType'])!.value,
      vin: this.editForm.get(['vin'])!.value,
      make: this.editForm.get(['make'])!.value,
      model: this.editForm.get(['model'])!.value,
      owner: this.editForm.get(['owner'])!.value,
    };
  }
}
