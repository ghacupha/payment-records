import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaymentRecordsGateSharedModule } from 'app/shared/shared.module';
import { PaymentsFileTypeComponent } from './payments-file-type.component';
import { PaymentsFileTypeDetailComponent } from './payments-file-type-detail.component';
import { PaymentsFileTypeUpdateComponent } from './payments-file-type-update.component';
import { PaymentsFileTypeDeleteDialogComponent } from './payments-file-type-delete-dialog.component';
import { paymentsFileTypeRoute } from './payments-file-type.route';

@NgModule({
  imports: [PaymentRecordsGateSharedModule, RouterModule.forChild(paymentsFileTypeRoute)],
  declarations: [
    PaymentsFileTypeComponent,
    PaymentsFileTypeDetailComponent,
    PaymentsFileTypeUpdateComponent,
    PaymentsFileTypeDeleteDialogComponent,
  ],
  entryComponents: [PaymentsFileTypeDeleteDialogComponent],
})
export class PaymentRecordsPaymentsFileTypeModule {}
