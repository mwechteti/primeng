export interface IPortfolioItemStatus {
    id?: number;
    label?: string;
  }
  
  export class PortfolioItemStatus implements IPortfolioItemStatus {
    constructor(
      public id?: number,
      public label?: string,
    ) {}
  }
  
  export function getPortfolioItemStatusIdentifier(portfolioItemStatus: IPortfolioItemStatus): number | undefined {
    return portfolioItemStatus.id;
  }