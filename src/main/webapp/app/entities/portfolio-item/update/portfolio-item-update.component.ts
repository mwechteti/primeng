import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { IPortfolio } from 'app/entities/portfolio/portfolio.model';
import { PortfolioService } from 'app/entities/portfolio/service/portfolio.service';
import { HttpVehicleService } from 'app/entities/vehicle/service/vehicle.service';
import { IVehicle } from 'app/entities/vehicle/vehicle.model';
import { IPortfolioItemStatus } from 'app/entities/portfolio-item-status/portfolio-item-status.model';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';
import { IPortfolioItem, PortfolioItem } from '../portfolio-item.model';
import { PortfolioItemService } from '../service/portfolio-item.service';


@Component({
  selector: 'jhi-portfolio-item-update',
  templateUrl: './portfolio-item-update.component.html',
})
export class PortfolioItemUpdateComponent implements OnInit {
  isSaving = false;

  vehiclesCollection: IVehicle[] = [];
  portfoliosSharedCollection: IPortfolio[] = [];
  portfolioItemStatuses: IPortfolioItemStatus[] = [];

  editForm = this.fb.group({
    id: [],
    vehicle: [],
    portfolioItemDetails: [],
    portfolio: [],
  });

  constructor(
    protected portfolioItemService: PortfolioItemService,
    protected vehicleService: HttpVehicleService,
    protected portfolioService: PortfolioService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe((portfolioItem: IPortfolioItem) => {
      // this is necessary to extract the entity from the wrapping object sent by the route resolve
      const entityToUpdate: IPortfolioItem = Object(portfolioItem)["portfolioItem"];
      this.updateForm(entityToUpdate);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const portfolioItem = this.createFromForm();
    if (portfolioItem.id !== undefined) {
      this.subscribeToSaveResponse(this.portfolioItemService.update(portfolioItem));
    } else {
      this.subscribeToSaveResponse(this.portfolioItemService.create(portfolioItem));
    }
  }

  trackVehicleById(index: number, item: IVehicle): number {
    return item.id!;
  }

  trackPortfolioById(index: number, item: IPortfolio): number {
    return item.id!;
  }

  trackPortfolioItemStatusById(index: number, item: IPortfolioItemStatus): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPortfolioItem>>): void {
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

  protected updateForm(portfolioItem: IPortfolioItem): void {
    this.editForm.patchValue({
      id: portfolioItem.id,
      vehicle: portfolioItem.vehicle,
      portfolio: portfolioItem.portfolio,
    });

    this.vehiclesCollection = this.vehicleService.addVehicleToCollectionIfMissing(this.vehiclesCollection, portfolioItem.vehicle);
    this.portfoliosSharedCollection = this.portfolioService.addPortfolioToCollectionIfMissing(
      this.portfoliosSharedCollection,
      portfolioItem.portfolio
    );
  }

  protected loadRelationshipsOptions(): void {
    this.vehicleService
      .query({ filter: 'portfolioitem-is-null' })
      .pipe(map((res: HttpResponse<IVehicle[]>) => res.body ?? []))
      .pipe(
        map((vehicles: IVehicle[]) => this.vehicleService.addVehicleToCollectionIfMissing(vehicles, this.editForm.get('vehicle')!.value as IVehicle))
      )
      .subscribe((vehicles: IVehicle[]) => (this.vehiclesCollection = vehicles));

    this.portfolioService
      .query()
      .pipe(map((res: HttpResponse<IPortfolio[]>) => res.body ?? []))
      .pipe(
        map((portfolios: IPortfolio[]) =>
          this.portfolioService.addPortfolioToCollectionIfMissing(portfolios, this.editForm.get('portfolio')!.value as IPortfolio)
        )
      )
      .subscribe((portfolios: IPortfolio[]) => (this.portfoliosSharedCollection = portfolios));
  }

  protected createFromForm(): IPortfolioItem {
    return {
      ...new PortfolioItem(),
      id: this.editForm.get(['id'])!.value,
      vehicle: this.editForm.get(['vehicle'])!.value,
      portfolio: this.editForm.get(['portfolio'])!.value,
    };
  }
}
