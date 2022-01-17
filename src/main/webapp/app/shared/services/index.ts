import { Provider } from '@angular/core';
import { VehicleService, HttpVehicleService } from 'app/entities/vehicle/service/vehicle.service';
import { WebSocketService } from './websocket.service';

export { HttpVehicleService } from 'app/entities/vehicle/service/vehicle.service';
export { IVehicle, VehicleSearchParams } from 'app/entities/vehicle/vehicle.model';
export { WebSocketService } from './websocket.service';

export const SHARED_SERVICES: Provider[] = [
    { provide: VehicleService, useClass: HttpVehicleService },
    { provide: WebSocketService, useClass: WebSocketService }
  ];