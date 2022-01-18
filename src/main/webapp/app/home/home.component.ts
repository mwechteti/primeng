import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { PortfolioItemDeleteDialogComponent } from 'app/entities/portfolio-item/delete/portfolio-item-delete-dialog.component';
import { IPortfolioItem } from 'app/entities/portfolio-item/portfolio-item.model';
import { PortfolioItemService } from 'app/entities/portfolio-item/service/portfolio-item.service';
import { IPortfolio } from 'app/entities/portfolio/portfolio.model';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';
import { Observable } from 'rxjs/internal/Observable';


@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  account: Account | null = null;
  selectedPortfolio?: IPortfolio;
  portfolios?: IPortfolio[];
  portfolioItems$?: Observable<IPortfolioItem[]>;
  isLoading = false;

  constructor(private accountService: AccountService, private userService: UserService,
    protected portfolioItemService: PortfolioItemService,
    protected httpClient: HttpClient,
    protected modalService: NgbModal) {
  }

  loadUserPortfolios(): void {
    if (this.account) {
      this.userService.getUserByLogin(this.account.login).subscribe(
        (res: IUser) => {
          this.portfolios = res.portfolios ?? [];
          this.selectedPortfolio = this.portfolios[0];

          this.loadPortfolioItems(this.selectedPortfolio);
        });
    }
  }

  loadPortfolioItems(selectedPortfolio: IPortfolio | undefined): void {
    this.isLoading = true;

    if (selectedPortfolio?.id) {
      this.portfolioItems$ = this.portfolioItemService.findByPortfolioId(selectedPortfolio.id);
    }
  }

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      this.account = account;
    });
    this.loadUserPortfolios();
  }

  trackId(index: number, item: IPortfolioItem): number {
    return item.id!;
  }

  delete(portfolioItem: IPortfolioItem): void {
    const modalRef = this.modalService.open(PortfolioItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.portfolioItem = portfolioItem;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadPortfolioItems(this.selectedPortfolio);
      }
    });
  }

}
