import { ILegalEntityType } from 'app/entities/legal-entity-type/legal-entity-type.model';
import { IVehicle } from 'app/entities/vehicle/vehicle.model';
import { IAddress } from 'app/entities/address/address.model';

export interface ILegalEntity {
  id?: number;
  name?: string;
  type?: ILegalEntityType | null;
  vehicles?: IVehicle[] | null;
  addresses?: IAddress[] | null;
}

export class LegalEntity implements ILegalEntity {
  constructor(
    public id?: number,
    public name?: string,
    public type?: ILegalEntityType | null,
    public vehicles?: IVehicle[] | null,
    public addresses?: IAddress[] | null
  ) {}
}

export function getLegalEntityIdentifier(legalEntity: ILegalEntity): number | undefined {
  return legalEntity.id;
}
