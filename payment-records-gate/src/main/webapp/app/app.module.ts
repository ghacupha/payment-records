import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { PaymentRecordsGateSharedModule } from 'app/shared/shared.module';
import { PaymentRecordsGateCoreModule } from 'app/core/core.module';
import { PaymentRecordsGateAppRoutingModule } from './app-routing.module';
import { PaymentRecordsGateHomeModule } from './home/home.module';
import { PaymentRecordsGateEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    PaymentRecordsGateSharedModule,
    PaymentRecordsGateCoreModule,
    PaymentRecordsGateHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    PaymentRecordsGateEntityModule,
    PaymentRecordsGateAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class PaymentRecordsGateAppModule {}
