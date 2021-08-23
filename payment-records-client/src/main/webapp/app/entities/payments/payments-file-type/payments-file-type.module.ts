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

import { PaymentRecordsClientSharedModule } from 'app/shared/shared.module';
import { PaymentsFileTypeComponent } from './payments-file-type.component';
import { PaymentsFileTypeDetailComponent } from './payments-file-type-detail.component';
import { PaymentsFileTypeUpdateComponent } from './payments-file-type-update.component';
import { PaymentsFileTypeDeleteDialogComponent } from './payments-file-type-delete-dialog.component';
import { paymentsFileTypeRoute } from './payments-file-type.route';

@NgModule({
  imports: [PaymentRecordsClientSharedModule, RouterModule.forChild(paymentsFileTypeRoute)],
  declarations: [
    PaymentsFileTypeComponent,
    PaymentsFileTypeDetailComponent,
    PaymentsFileTypeUpdateComponent,
    PaymentsFileTypeDeleteDialogComponent,
  ],
  entryComponents: [PaymentsFileTypeDeleteDialogComponent],
})
export class PaymentRecordsClientPaymentsFileTypeModule {}
