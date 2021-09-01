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
      {
        path: 'payment-label',
        loadChildren: () => import('./paymentRecords/payment-label/payment-label.module').then(m => m.PaymentRecordsPaymentLabelModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class PaymentRecordsGateEntityModule {}
