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
