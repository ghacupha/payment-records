import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PaymentRecordsClientSharedModule } from 'app/shared/shared.module';
import { RouterModule, Routes } from '@angular/router';
import { PaymentRecordsClientAboutModule } from 'app/payment-records/about/about.module';

export const routes: Routes = [];

@NgModule({
  imports: [CommonModule, PaymentRecordsClientSharedModule, RouterModule.forChild(routes), PaymentRecordsClientAboutModule],
  exports: [RouterModule]
})
export class PaymentRecordsBespokeModule{}
