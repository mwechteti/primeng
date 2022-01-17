export interface IPortfolioItemStatus {
    id?: number;
    label?: string;
  }
  
  export class PortfolioItemStatus implements IPortfolioItemStatus {
    constructor(
      public id?: number,
      public vehicle?: string,
    ) {}
  }
  
  export function getPortfolioItemStatusIdentifier(portfolioItemStatus: IPortfolioItemStatus): number | undefined {
    return portfolioItemStatus.id;
  }