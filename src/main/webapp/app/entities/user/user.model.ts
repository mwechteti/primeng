import { IPortfolio } from "../portfolio/portfolio.model";

export interface IUser {
  id?: string;
  login?: string;
  portfolios?: IPortfolio[] | null;
}

export class User implements IUser {
  constructor(public id: string, public login: string, public portfolios: IPortfolio[] | null) {}
}

export function getUserIdentifier(user: IUser): string | undefined {
  return user.id;
}
