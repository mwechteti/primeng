import { ChangeDetectionStrategy } from '@angular/compiler/src/core';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpVehicleService } from 'app/entities/vehicle/service/vehicle.service';
import { IVehicle } from 'app/entities/vehicle/vehicle.model';
import { Observable } from 'rxjs';
import { switchMap } from 'rxjs/operators';


@Component({
  selector: 'jhi-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss',],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SearchComponent {
  // Declare an Observable for vehicles
  readonly vehicles$: Observable<IVehicle[]>;

  constructor(
    private vehicleService: HttpVehicleService,
    private route: ActivatedRoute
  ) {
    this.vehicles$ = this.route.queryParams.pipe(
      switchMap(queryParams => this.vehicleService.search(queryParams))
    );
  }
}