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
