import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { PaymentRecordsClientSharedModule } from 'app/shared/shared.module';
import { PaymentRecordsClientCoreModule } from 'app/core/core.module';
import { PaymentRecordsClientAppRoutingModule } from './app-routing.module';
import { PaymentRecordsClientHomeModule } from './home/home.module';
import { PaymentRecordsClientEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    PaymentRecordsClientSharedModule,
    PaymentRecordsClientCoreModule,
    PaymentRecordsClientHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    PaymentRecordsClientEntityModule,
    PaymentRecordsClientAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class PaymentRecordsClientAppModule {}
