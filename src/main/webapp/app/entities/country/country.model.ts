export interface ICountry {
  id?: number;
  name?: string;
  code?: string;
}

export class Country implements ICountry {
  constructor(public id?: number, public name?: string, public code?: string) {}
}

export function getCountryIdentifier(country: ICountry): number | undefined {
  return country.id;
}
