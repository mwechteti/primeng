jest.mock('@angular/router');
jest.mock('app/login/login.service');

import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { Router } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { LoginService } from 'app/login/login.service';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { MainContentComponent } from './main-content.component';



describe('Component Tests', () => {
  describe('Main Content Component', () => {
    let comp: MainContentComponent;
    let fixture: ComponentFixture<MainContentComponent>;
    let accountService: AccountService;
    let profileService: ProfileService;
    const account: Account = {
      activated: true,
      authorities: [],
      email: '',
      firstName: 'John',
      langKey: '',
      lastName: 'Doe',
      login: 'john.doe',
      imageUrl: '',
    };

    beforeEach(
      waitForAsync(() => {
        TestBed.configureTestingModule({
          imports: [HttpClientTestingModule, NgxWebstorageModule.forRoot(), TranslateModule.forRoot()],
          declarations: [MainContentComponent],
          providers: [Router, LoginService],
        })
          .overrideTemplate(MainContentComponent, '')
          .compileComponents();
      })
    );

    beforeEach(() => {
      fixture = TestBed.createComponent(MainContentComponent);
      comp = fixture.componentInstance;
      accountService = TestBed.inject(AccountService);
      profileService = TestBed.inject(ProfileService);
    });

  });
});
