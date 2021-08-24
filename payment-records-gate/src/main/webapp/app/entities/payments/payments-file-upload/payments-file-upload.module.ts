import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaymentRecordsGateSharedModule } from 'app/shared/shared.module';
import { PaymentsFileUploadComponent } from './payments-file-upload.component';
import { PaymentsFileUploadDetailComponent } from './payments-file-upload-detail.component';
import { PaymentsFileUploadUpdateComponent } from './payments-file-upload-update.component';
import { PaymentsFileUploadDeleteDialogComponent } from './payments-file-upload-delete-dialog.component';
import { paymentsFileUploadRoute } from './payments-file-upload.route';

@NgModule({
  imports: [PaymentRecordsGateSharedModule, RouterModule.forChild(paymentsFileUploadRoute)],
  declarations: [
    PaymentsFileUploadComponent,
    PaymentsFileUploadDetailComponent,
    PaymentsFileUploadUpdateComponent,
    PaymentsFileUploadDeleteDialogComponent,
  ],
  entryComponents: [PaymentsFileUploadDeleteDialogComponent],
})
export class PaymentRecordsPaymentsFileUploadModule {}
