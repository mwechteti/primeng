import { IPortfolioItem } from 'app/entities/portfolio-item/portfolio-item.model';

export interface IPortfolio {
  id?: number;
  label?: string;
  portfolioItems?: IPortfolioItem[] | null;
}

export class Portfolio implements IPortfolio {
  constructor(public id?: number, public label?: string, public portfolioItems?: IPortfolioItem[] | null) {}
}

export function getPortfolioIdentifier(portfolio: IPortfolio): number | undefined {
  return portfolio.id;
}
