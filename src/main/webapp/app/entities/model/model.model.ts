import { IMake } from 'app/entities/make/make.model';

export interface IModel {
  id?: number;
  label?: string;
  year?: number;
  make?: IMake | null;
}

export class Model implements IModel {
  constructor(public id?: number, public label?: string, public year?: number, public make?: IMake | null) {}
}

export function getModelIdentifier(model: IModel): number | undefined {
  return model.id;
}
