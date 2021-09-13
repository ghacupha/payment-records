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

import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPaymentLabel, PaymentLabel } from 'app/shared/model/paymentRecords/payment-label.model';
import { PaymentLabelService } from './payment-label.service';

@Component({
  selector: 'jhi-payment-label-update',
  templateUrl: './payment-label-update.component.html',
})
export class PaymentLabelUpdateComponent implements OnInit {
  isSaving = false;
  scheduleDp: any;

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    comments: [],
    schedule: [],
  });

  constructor(protected paymentLabelService: PaymentLabelService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentLabel }) => {
      this.updateForm(paymentLabel);
    });
  }

  updateForm(paymentLabel: IPaymentLabel): void {
    this.editForm.patchValue({
      id: paymentLabel.id,
      description: paymentLabel.description,
      comments: paymentLabel.comments,
      schedule: paymentLabel.schedule,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentLabel = this.createFromForm();
    if (paymentLabel.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentLabelService.update(paymentLabel));
    } else {
      this.subscribeToSaveResponse(this.paymentLabelService.create(paymentLabel));
    }
  }

  private createFromForm(): IPaymentLabel {
    return {
      ...new PaymentLabel(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      schedule: this.editForm.get(['schedule'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentLabel>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
