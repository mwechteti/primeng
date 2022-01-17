import { IVehicle } from 'app/entities/vehicle/vehicle.model';
import { IPortfolio } from 'app/entities/portfolio/portfolio.model';
import * as dayjs from 'dayjs';

export interface IPortfolioItem {
  id?: number;
  vehicle?: IVehicle | null;
  portfolio?: IPortfolio | null;
  stockEntranceDate?: dayjs.Dayjs;
}

export class PortfolioItem implements IPortfolioItem {
  constructor(
    public id?: number,
    public vehicle?: IVehicle | null,
    public portfolio?: IPortfolio | null,
    public stockEntranceDate?: dayjs.Dayjs,
  ) {}
}

export function getPortfolioItemIdentifier(portfolioItem: IPortfolioItem): number | undefined {
  return portfolioItem.id;
}
