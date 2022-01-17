import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpVehicleService } from '../service/vehicle.service';
import { IVehicle } from '../vehicle.model';


@Component({
  templateUrl: './vehicle-delete-dialog.component.html',
})
export class VehicleDeleteDialogComponent {
  vehicle?: IVehicle;

  constructor(protected vehicleService: HttpVehicleService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.vehicleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
