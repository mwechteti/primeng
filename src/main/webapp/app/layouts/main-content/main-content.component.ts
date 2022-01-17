import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { Observable } from 'rxjs/internal/Observable';
import { Subscription } from 'rxjs/internal/Subscription';

declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}

export const ROUTES: RouteInfo[] = [
  { path: '/dashboard', title: 'Dashboard', icon: 'dashboard', class: '' },
  { path: '/', title: 'Portfolio', icon: 'list', class: '' },
];

export const ENTITIES: RouteInfo[] = [
  { path: '/vehicle', title: 'Vehicle', icon: '', class: '' },
  { path: '/legal-entity', title: 'Legal Entity', icon: '', class: '' },
  { path: '/legal-entity-type', title: 'Legal Entity Type', icon: '', class: '' },
  { path: '/make', title: 'Make', icon: '', class: '' },
  { path: '/model', title: 'Model', icon: '', class: '' },
  { path: '/address', title: 'Address', icon: '', class: '' },
  { path: '/country', title: 'Country', icon: '', class: '' },
  { path: '/portfolio', title: 'Portfolio', icon: '', class: '' },
];

@Component({
  selector: 'jhi-main-content',
  templateUrl: './main-content.component.html',
  styleUrls: ['./main-content.component.scss'],
})
export class MainContentComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  menuItems: RouteInfo[] | undefined;
  navItems: RouteInfo[] | undefined;
  panelOpenState = false;

  // Events coming from the parent
  @Input()
  events!: Observable<void>;

  sidenavOpened = true;
  private eventsSubscription!: Subscription;

  constructor(private accountService: AccountService, private loginService: LoginService) { }

  isUserAuthorized(): boolean {
    return true;
  }
  isUserLogged(): boolean {
    return true;
  }

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.account = account));
    this.menuItems = ROUTES;
    this.navItems = ENTITIES;

    this.eventsSubscription = this.events.subscribe(() => {
      // The action to perform when the event is triggered
      this.sidenavOpened = !this.sidenavOpened;
    })
  };

  ngOnDestroy(): void {
    this.eventsSubscription.unsubscribe();
  }

  login(): void {
    this.loginService.login();
  }

}
