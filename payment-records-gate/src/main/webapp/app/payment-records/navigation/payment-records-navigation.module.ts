import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {PaymentRecordsGateSharedModule} from "app/shared/shared.module";
import {RouterModule, Routes} from "@angular/router";
import {PaymentFilesNavigationComponent} from "app/payment-records/navigation/files/payment-files-navigation.component";
import {PaymentRecordsGateCoreModule} from "app/core/core.module";
import {PaymentRecordsGateSharedLibsModule} from "app/shared/shared-libs.module";
import {PaymentsNavigationComponent} from "app/payment-records/navigation/payments/payments-navigation.component";
import {NewPaymentNavigationComponent} from "app/payment-records/navigation/new-payment-navigation/new-payment-navigation.component";

export const routes: Routes = [];

@NgModule({
  declarations: [
    PaymentFilesNavigationComponent,
    PaymentsNavigationComponent,
    NewPaymentNavigationComponent
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
    NewPaymentNavigationComponent
  ]
})
export class PaymentRecordsNavigationModule {}
