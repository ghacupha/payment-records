import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaymentRecordsGateSharedModule } from 'app/shared/shared.module';
import { PaymentsMessageTokenComponent } from './payments-message-token.component';
import { PaymentsMessageTokenDetailComponent } from './payments-message-token-detail.component';
import { PaymentsMessageTokenUpdateComponent } from './payments-message-token-update.component';
import { PaymentsMessageTokenDeleteDialogComponent } from './payments-message-token-delete-dialog.component';
import { paymentsMessageTokenRoute } from './payments-message-token.route';

@NgModule({
  imports: [PaymentRecordsGateSharedModule, RouterModule.forChild(paymentsMessageTokenRoute)],
  declarations: [
    PaymentsMessageTokenComponent,
    PaymentsMessageTokenDetailComponent,
    PaymentsMessageTokenUpdateComponent,
    PaymentsMessageTokenDeleteDialogComponent,
  ],
  entryComponents: [PaymentsMessageTokenDeleteDialogComponent],
})
export class PaymentRecordsPaymentsMessageTokenModule {}
