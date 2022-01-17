import { ICountry } from 'app/entities/country/country.model';
import { ILegalEntity } from 'app/entities/legal-entity/legal-entity.model';

export interface IAddress {
  id?: number;
  label?: string;
  postCode?: string;
  addressLine1?: string;
  addressLine2?: string | null;
  city?: string;
  main?: boolean;
  country?: ICountry | null;
  legalEntity?: ILegalEntity | null;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public label?: string,
    public postCode?: string,
    public addressLine1?: string,
    public addressLine2?: string | null,
    public city?: string,
    public main?: boolean,
    public country?: ICountry | null,
    public legalEntity?: ILegalEntity | null
  ) {
    this.main = this.main ?? false;
  }
}

export function getAddressIdentifier(address: IAddress): number | undefined {
  return address.id;
}
