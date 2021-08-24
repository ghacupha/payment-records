import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'payment',
        loadChildren: () => import('./paymentRecords/payment/payment.module').then(m => m.PaymentRecordsPaymentModule),
      },
      {
        path: 'payments-file-type',
        loadChildren: () =>
          import('./payments/payments-file-type/payments-file-type.module').then(m => m.PaymentRecordsPaymentsFileTypeModule),
      },
      {
        path: 'payments-file-upload',
        loadChildren: () =>
          import('./payments/payments-file-upload/payments-file-upload.module').then(m => m.PaymentRecordsPaymentsFileUploadModule),
      },
      {
        path: 'payments-message-token',
        loadChildren: () =>
          import('./payments/payments-message-token/payments-message-token.module').then(m => m.PaymentRecordsPaymentsMessageTokenModule),
      },
      {
        path: 'currency-table',
        loadChildren: () => import('./payments/currency-table/currency-table.module').then(m => m.PaymentRecordsCurrencyTableModule),
      },
      {
        path: 'placeholder',
        loadChildren: () => import('./paymentRecords/placeholder/placeholder.module').then(m => m.PaymentRecordsPlaceholderModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class PaymentRecordsGateEntityModule {}
