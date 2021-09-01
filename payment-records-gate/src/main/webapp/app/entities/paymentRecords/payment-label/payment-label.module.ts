import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaymentRecordsGateSharedModule } from 'app/shared/shared.module';
import { PaymentLabelComponent } from './payment-label.component';
import { PaymentLabelDetailComponent } from './payment-label-detail.component';
import { PaymentLabelUpdateComponent } from './payment-label-update.component';
import { PaymentLabelDeleteDialogComponent } from './payment-label-delete-dialog.component';
import { paymentLabelRoute } from './payment-label.route';

@NgModule({
  imports: [PaymentRecordsGateSharedModule, RouterModule.forChild(paymentLabelRoute)],
  declarations: [PaymentLabelComponent, PaymentLabelDetailComponent, PaymentLabelUpdateComponent, PaymentLabelDeleteDialogComponent],
  entryComponents: [PaymentLabelDeleteDialogComponent],
})
export class PaymentRecordsPaymentLabelModule {}
