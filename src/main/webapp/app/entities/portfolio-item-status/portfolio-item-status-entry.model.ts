import * as dayjs from "dayjs";
import { IPortfolioItemStatus } from "./portfolio-item-status.model";

export interface IPortfolioItemStatusEntry {
    id: number;
    status?: IPortfolioItemStatus;
    statusCreationDate?: dayjs.Dayjs;
  }
  
  export class PortfolioItemStatusEntry implements IPortfolioItemStatusEntry {
    constructor(
      public id: number,
      public status?: IPortfolioItemStatus,
      public statusCreationDate?: dayjs.Dayjs,
    ) {}
  }
  
  export function getPortfolioItemStatusIdentifier(portfolioItemStatus: IPortfolioItemStatus): number | undefined {
    return portfolioItemStatus.id;
  }