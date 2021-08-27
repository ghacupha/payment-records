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
