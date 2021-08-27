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

import { IPaymentsMessageToken, PaymentsMessageToken } from 'app/shared/model/payments/payments-message-token.model';
import { PaymentsMessageTokenService } from './payments-message-token.service';

@Component({
  selector: 'jhi-payments-message-token-update',
  templateUrl: './payments-message-token-update.component.html',
})
export class PaymentsMessageTokenUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    description: [],
    timeSent: [null, [Validators.required]],
    tokenValue: [null, [Validators.required]],
    received: [],
    actioned: [],
    contentFullyEnqueued: [],
  });

  constructor(
    protected paymentsMessageTokenService: PaymentsMessageTokenService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentsMessageToken }) => {
      this.updateForm(paymentsMessageToken);
    });
  }

  updateForm(paymentsMessageToken: IPaymentsMessageToken): void {
    this.editForm.patchValue({
      id: paymentsMessageToken.id,
      description: paymentsMessageToken.description,
      timeSent: paymentsMessageToken.timeSent,
      tokenValue: paymentsMessageToken.tokenValue,
      received: paymentsMessageToken.received,
      actioned: paymentsMessageToken.actioned,
      contentFullyEnqueued: paymentsMessageToken.contentFullyEnqueued,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentsMessageToken = this.createFromForm();
    if (paymentsMessageToken.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentsMessageTokenService.update(paymentsMessageToken));
    } else {
      this.subscribeToSaveResponse(this.paymentsMessageTokenService.create(paymentsMessageToken));
    }
  }

  private createFromForm(): IPaymentsMessageToken {
    return {
      ...new PaymentsMessageToken(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      timeSent: this.editForm.get(['timeSent'])!.value,
      tokenValue: this.editForm.get(['tokenValue'])!.value,
      received: this.editForm.get(['received'])!.value,
      actioned: this.editForm.get(['actioned'])!.value,
      contentFullyEnqueued: this.editForm.get(['contentFullyEnqueued'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentsMessageToken>>): void {
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
