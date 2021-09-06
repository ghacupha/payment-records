///
/// Payment Records - Payment records is part of the ERP System
/// Copyright © 2021 Edwin Njeru (mailnjeru@gmail.com)
///
/// This program is free software: you can redistribute it and/or modify
/// it under the terms of the GNU General Public License as published by
/// the Free Software Foundation, either version 3 of the License, or
/// (at your option) any later version.
///
/// This program is distributed in the hope that it will be useful,
/// but WITHOUT ANY WARRANTY; without even the implied warranty of
/// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
/// GNU General Public License for more details.
///
/// You should have received a copy of the GNU General Public License
/// along with this program. If not, see <http://www.gnu.org/licenses/>.
///

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
import {PaymentRecordsBespokeModule} from "app/payment-records/paymentRecordsBespoke.module";
import {StoreModule} from "@ngrx/store";

@NgModule({
  imports: [
    BrowserModule,
    PaymentRecordsGateSharedModule,
    PaymentRecordsGateCoreModule,
    PaymentRecordsGateHomeModule,
    PaymentRecordsBespokeModule,
    StoreModule.forRoot({}, {runtimeChecks: {
        strictStateImmutability: true,
        strictActionImmutability: true,
        strictStateSerializability: true,
        strictActionSerializability: true,
        strictActionWithinNgZone: true,
        strictActionTypeUniqueness: true,
    }}),
    // jhipster-needle-angular-add-module JHipster will add new module here
    PaymentRecordsGateEntityModule,
    PaymentRecordsGateAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class PaymentRecordsGateAppModule {}
