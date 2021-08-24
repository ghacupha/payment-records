import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PaymentRecordsGateSharedModule } from 'app/shared/shared.module';
import { PlaceholderComponent } from './placeholder.component';
import { PlaceholderDetailComponent } from './placeholder-detail.component';
import { PlaceholderUpdateComponent } from './placeholder-update.component';
import { PlaceholderDeleteDialogComponent } from './placeholder-delete-dialog.component';
import { placeholderRoute } from './placeholder.route';

@NgModule({
  imports: [PaymentRecordsGateSharedModule, RouterModule.forChild(placeholderRoute)],
  declarations: [PlaceholderComponent, PlaceholderDetailComponent, PlaceholderUpdateComponent, PlaceholderDeleteDialogComponent],
  entryComponents: [PlaceholderDeleteDialogComponent],
})
export class PaymentRecordsPlaceholderModule {}
