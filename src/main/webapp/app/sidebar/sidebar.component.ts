import { Component, OnInit } from '@angular/core';
import { VERSION } from 'app/app.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';

declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}
export const ROUTES: RouteInfo[] = [
  { path: '/dashboard', title: 'Dashboard', icon: 'pe-7s-graph', class: '' },
  { path: '/portfolio', title: 'Portfolio', icon: 'pe-7s-user', class: '' },
  { path: '/notifications', title: 'Notifications', icon: 'pe-7s-bell', class: '' },
];

@Component({
  selector: 'jhi-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent implements OnInit {
  menuItems: any[] | undefined;
  version = '';
  account: Account | null = null;

  constructor(private accountService: AccountService) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : 'v' + VERSION;
    }
  }

  ngOnInit(): void {
    this.menuItems = ROUTES;
  }

  isUserAuthorized(): boolean {
    return true;
  }
  isUserLogged(): boolean {
    return true;
  }
}
