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

import { IPayment, Payment } from 'app/shared/model/paymentRecords/payment.model';
import { PaymentService } from './payment.service';
import { IPlaceholder } from 'app/shared/model/paymentRecords/placeholder.model';
import { PlaceholderService } from 'app/entities/paymentRecords/placeholder/placeholder.service';
import { IPaymentLabel } from 'app/shared/model/paymentRecords/payment-label.model';
import { PaymentLabelService } from 'app/entities/paymentRecords/payment-label/payment-label.service';
import {PaymentUpdateFormStateService} from "app/payment-records/state/payment-update-form-state.service";
import {select, Store} from "@ngrx/store";
import {State} from "app/payment-records/store/global-store.definition";
import {
  copyingPaymentStatus,
  creatingPaymentStatus,
  editingPaymentStatus, updateSelectedPayment
} from "app/payment-records/store/update-menu-status.selectors";
import {paymentUpdateCancelButtonClicked} from "app/payment-records/store/update-menu-status.actions";

type SelectableEntity = IPlaceholder | IPaymentLabel;

@Component({
  selector: 'jhi-payment-update',
  templateUrl: './payment-update.component.html',
})
export class PaymentUpdateComponent implements OnInit {
  isSaving = false;
  placeholders: IPlaceholder[] = [];
  paymentlabels: IPaymentLabel[] = [];
  transactionDateDp: any;

  selectedPayment: IPayment = {...new Payment()};
  weAreCopyingAPayment = false;
  weAreEditingAPayment = false;
  weAreCreatingAPayment = false;

  editForm = this.fb.group({
    id: [],
    paymentsCategory: [],
    transactionNumber: [],
    transactionDate: [],
    transactionCurrency: [],
    transactionAmount: [null, [Validators.required, Validators.min(0)]],
    beneficiary: [],
    placeholders: [],
    paymentLabels: [],
  });

  constructor(
    protected paymentService: PaymentService,
    protected placeholderService: PlaceholderService,
    protected paymentLabelService: PaymentLabelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private formUpdateStateService: PaymentUpdateFormStateService,
    protected store: Store<State>
  ) {
    /* this.formUpdateStateService.weAreCopyingState$.subscribe(copyState => {
      this.weAreCopyingAPayment = copyState;
    }); */
    /* this.formUpdateStateService.weAreEditingState$.subscribe(editState => {
      this.weAreEditingAPayment = editState;
    }); */

    /* this.formUpdateStateService.weAreCreatingState$.subscribe(createState => {
      this.weAreCreatingAPayment = createState;
    }); */

    /* this.formUpdateStateService.selectedPayment$.subscribe(pyt => {
      this.selectedPayment = pyt;
    }); */

    this.store.pipe(select(copyingPaymentStatus)).subscribe(stat => this.weAreCopyingAPayment = stat);
    this.store.pipe(select(editingPaymentStatus)).subscribe(stat => this.weAreEditingAPayment = stat);
    this.store.pipe(select(creatingPaymentStatus)).subscribe(stat => this.weAreCreatingAPayment = stat);
    this.store.pipe(select(updateSelectedPayment)).subscribe(pyt => this.selectedPayment = pyt);
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ payment }) => {
      this.updateForm(payment);

      this.placeholderService.query().subscribe((res: HttpResponse<IPlaceholder[]>) => (this.placeholders = res.body || []));

      this.paymentLabelService.query().subscribe((res: HttpResponse<IPaymentLabel[]>) => (this.paymentlabels = res.body || []));
    });
  }

  updateForm(payment: IPayment): void {
    this.editForm.patchValue({
      id: payment.id,
      paymentsCategory: payment.paymentsCategory,
      transactionNumber: payment.transactionNumber,
      transactionDate: payment.transactionDate,
      transactionCurrency: payment.transactionCurrency,
      transactionAmount: payment.transactionAmount,
      beneficiary: payment.beneficiary,
      placeholders: payment.placeholders,
      paymentLabels: payment.paymentLabels,
    });
  }

  previousState(): void {
    this.store.dispatch(paymentUpdateCancelButtonClicked());
    window.history.back();
  }

  edit(): void {
    this.save();
    this.formUpdateStateService.paymentEditedSuccessfully();
  }

  save(): void {
    this.isSaving = true;
    const payment = this.createFromForm();
    if (payment.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentService.update(payment));
    } else {
      this.subscribeToSaveResponse(this.paymentService.create(payment));
    }
  }

  copy(): void {
    this.isSaving = true;
    const payment = this.copyFromForm();
    this.subscribeToCopyResponse(this.paymentService.create(payment));
  }

  private copyFromForm(): IPayment {
    return {
      ...new Payment(),
      paymentsCategory: this.editForm.get(['paymentsCategory'])!.value,
      transactionNumber: this.editForm.get(['transactionNumber'])!.value,
      transactionDate: this.editForm.get(['transactionDate'])!.value,
      transactionCurrency: this.editForm.get(['transactionCurrency'])!.value,
      transactionAmount: this.editForm.get(['transactionAmount'])!.value,
      beneficiary: this.editForm.get(['beneficiary'])!.value,
      placeholders: this.editForm.get(['placeholders'])!.value,
      paymentLabels: this.editForm.get(['paymentLabels'])!.value,
    };
  }

  private createFromForm(): IPayment {
    return {
      ...new Payment(),
      id: this.editForm.get(['id'])!.value,
      paymentsCategory: this.editForm.get(['paymentsCategory'])!.value,
      transactionNumber: this.editForm.get(['transactionNumber'])!.value,
      transactionDate: this.editForm.get(['transactionDate'])!.value,
      transactionCurrency: this.editForm.get(['transactionCurrency'])!.value,
      transactionAmount: this.editForm.get(['transactionAmount'])!.value,
      beneficiary: this.editForm.get(['beneficiary'])!.value,
      placeholders: this.editForm.get(['placeholders'])!.value,
      paymentLabels: this.editForm.get(['paymentLabels'])!.value,
    };
  }

  protected subscribeToCopyResponse(result: Observable<HttpResponse<IPayment>>): void {
    result.subscribe(
      () => this.onCopySuccess(),
      () => this.onSaveError()
    );
  }

  protected onCopySuccess(): void {
    this.isSaving = false;
    this.formUpdateStateService.paymentCopiedSuccessfully();
    this.previousState();
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPayment>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.formUpdateStateService.paymentSavedSuccessfully();
    this.previousState();
  }

  protected onSaveError(): void {
    this.formUpdateStateService.paymentUpdateFormErrored();
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: SelectableEntity[], option: SelectableEntity): SelectableEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
