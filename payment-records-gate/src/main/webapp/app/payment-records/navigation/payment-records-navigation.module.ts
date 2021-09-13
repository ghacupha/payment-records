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

import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {PaymentRecordsGateSharedModule} from "app/shared/shared.module";
import {RouterModule, Routes} from "@angular/router";
import {PaymentFilesNavigationComponent} from "app/payment-records/navigation/files/payment-files-navigation.component";
import {PaymentRecordsGateCoreModule} from "app/core/core.module";
import {PaymentRecordsGateSharedLibsModule} from "app/shared/shared-libs.module";
import {PaymentsNavigationComponent} from "app/payment-records/navigation/payments/payments-navigation.component";
import {NewPaymentNavigationComponent} from "app/payment-records/navigation/new-payment-navigation/new-payment-navigation.component";
import {HomeButtonNavigationComponent} from "app/payment-records/navigation/home-button-navigation/home-button-navigation.component";
import {PaymentLabelNavigationComponent} from "app/payment-records/navigation/payment-label/payment-label-navigation.component";
import {NavigationMenusComponent} from "app/payment-records/navigation/navigation-menus/navigation-menus.component";

export const routes: Routes = [];

@NgModule({
  declarations: [
    PaymentFilesNavigationComponent,
    PaymentsNavigationComponent,
    NewPaymentNavigationComponent,
    HomeButtonNavigationComponent,
    PaymentLabelNavigationComponent,
    NavigationMenusComponent
  ],
  imports: [
    CommonModule,
    PaymentRecordsGateSharedModule,
    PaymentRecordsGateSharedLibsModule,
    RouterModule.forChild(routes),
    PaymentRecordsGateCoreModule
  ],
  exports: [
    PaymentFilesNavigationComponent,
    PaymentsNavigationComponent,
    NewPaymentNavigationComponent,
    HomeButtonNavigationComponent,
    PaymentLabelNavigationComponent,
    NavigationMenusComponent
  ]
})
export class PaymentRecordsNavigationModule {}
