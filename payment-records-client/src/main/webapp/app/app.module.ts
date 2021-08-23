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
