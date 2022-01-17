import { registerLocaleData } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import locale from '@angular/common/locales/en';
import { LOCALE_ID, NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatSelectModule } from '@angular/material/select';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { BrowserModule, Title } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { ServiceWorkerModule } from '@angular/service-worker';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { NgbDateAdapter, NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { MissingTranslationHandler, TranslateLoader, TranslateModule, TranslateService } from '@ngx-translate/core';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { httpInterceptorProviders } from 'app/core/interceptor/index';
import { SharedModule } from 'app/shared/shared.module';
import * as dayjs from 'dayjs';
import { NgxWebstorageModule, SessionStorageService } from 'ngx-webstorage';
import { AppRoutingModule, routes } from './app-routing.module';
import { SERVER_API_URL } from './app.constants';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { NgbDateDayjsAdapter } from './config/datepicker-adapter';
import './config/dayjs';
import { fontAwesomeIcons } from './config/font-awesome-icons';
import { missingTranslationHandler, translatePartialLoader } from './config/translation.config';
import { DashboardComponent } from './dashboard/dashboard.component';
import { EntityRoutingModule } from './entities/entity-routing.module';
import { HomeModule } from './home/home.module';
import { ErrorComponent } from './layouts/error/error.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { MainContentComponent } from './layouts/main-content/main-content.component';
import { MainComponent } from './layouts/main/main.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { SearchFormModule } from './shared/components';
import { ActiveMenuDirective } from './sidebar/active-menu.directive';
import { SidebarComponent } from './sidebar/sidebar.component';



@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    MatMenuModule,
    MatSidenavModule,
    MatListModule,
    MatSelectModule,
    MatExpansionModule,
    FlexLayoutModule,
    FormsModule,
    SearchFormModule,
    SharedModule,
    HomeModule,
    // Invokes forRoot() and exports the router configuration so it can be imported by the root module
    RouterModule.forRoot(routes),
    // jhipster-needle-angular-add-module JHipster will add new module here
    EntityRoutingModule,
    AppRoutingModule,
    // Set this to true to enable service worker (PWA)
    ServiceWorkerModule.register('ngsw-worker.js', { enabled: false }),
    // Http client module will be used to get the data
    HttpClientModule,
    NgxWebstorageModule.forRoot({ prefix: 'jhi', separator: '-', caseSensitive: true }),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: translatePartialLoader,
        deps: [HttpClient],
      },
      missingTranslationHandler: {
        provide: MissingTranslationHandler,
        useFactory: missingTranslationHandler,
      },
    }),
  ],
  providers: [
    Title,
    { provide: LOCALE_ID, useValue: 'en' },
    { provide: NgbDateAdapter, useClass: NgbDateDayjsAdapter },
    // { provide: LocationStrategy, useClass: HashLocationStrategy }, // the chosen location strategy appearing in the URL can be changed from Path (default) to Hash
    httpInterceptorProviders,
  ],
  declarations: [
    MainComponent,
    MainContentComponent,
    SidebarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent,
    DashboardComponent,

  ],
  bootstrap: [MainComponent],
})
export class AppModule {
  constructor(
    applicationConfigService: ApplicationConfigService,
    iconLibrary: FaIconLibrary,
    dpConfig: NgbDatepickerConfig,
    translateService: TranslateService,
    sessionStorageService: SessionStorageService
  ) {
    applicationConfigService.setEndpointPrefix(SERVER_API_URL);
    registerLocaleData(locale);
    iconLibrary.addIcons(...fontAwesomeIcons);
    dpConfig.minDate = { year: dayjs().subtract(100, 'year').year(), month: 1, day: 1 };
    translateService.setDefaultLang('en');
    // if user have changed language and navigates away from the application and back to the application then use previously chosen language
    const langKey: string = sessionStorageService.retrieve('locale') ?? 'en';
    translateService.use(langKey);
  }
}
