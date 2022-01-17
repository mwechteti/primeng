import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVehicle } from '../vehicle.model';
import { HttpVehicleService } from '../service/vehicle.service';
import { VehicleDeleteDialogComponent } from '../delete/vehicle-delete-dialog.component';
import { Observable } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { filter, map, switchMap } from 'rxjs/operators';

@Component({
  selector: 'jhi-vehicle',
  templateUrl: './vehicle.component.html',
})
export class VehicleComponent implements OnInit {
  vehicles?: IVehicle[];
  isLoading = false;
  readonly vehicle$: Observable<IVehicle>;
  readonly suggestedVehicles$: Observable<IVehicle[]>;

  constructor(
    private route: ActivatedRoute,
    protected httpVehicleService: HttpVehicleService,
    protected modalService: NgbModal) {
    this.vehicle$ = this.route.paramMap
      .pipe(
        map(params => parseInt(params.get('vehicleId') ?? '', 10)),
        filter(vehicleId => Boolean(vehicleId)),
        switchMap(vehicleId => this.httpVehicleService.getById(vehicleId))
      );

    this.suggestedVehicles$ = this.httpVehicleService.getAll();
  }

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.isLoading = true;

    this.httpVehicleService.query().subscribe(
      (res: HttpResponse<IVehicle[]>) => {
        this.isLoading = false;
        this.vehicles = res.body ?? [];
      }
    );
  }

  trackId(index: number, item: IVehicle): number {
    return item.id!;
  }

  delete(vehicle: IVehicle): void {
    const modalRef = this.modalService.open(VehicleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.vehicle = vehicle;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
