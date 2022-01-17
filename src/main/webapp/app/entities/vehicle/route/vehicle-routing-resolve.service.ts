import { HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';
import { HttpVehicleService } from '../service/vehicle.service';
import { IVehicle, Vehicle } from '../vehicle.model';


@Injectable({ providedIn: 'root' })
export class VehicleRoutingResolveService implements Resolve<IVehicle> {
  constructor(protected service: HttpVehicleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IVehicle> | Observable<never> {
    const id: number = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((vehicle: HttpResponse<Vehicle>) => {
          if (vehicle.body) {
            return of(vehicle.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Vehicle());
  }
}
