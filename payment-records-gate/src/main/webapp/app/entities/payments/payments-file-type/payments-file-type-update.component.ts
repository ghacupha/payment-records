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
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPaymentsFileType, PaymentsFileType } from 'app/shared/model/payments/payments-file-type.model';
import { PaymentsFileTypeService } from './payments-file-type.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IPlaceholder } from 'app/shared/model/paymentRecords/placeholder.model';
import { PlaceholderService } from 'app/entities/paymentRecords/placeholder/placeholder.service';

@Component({
  selector: 'jhi-payments-file-type-update',
  templateUrl: './payments-file-type-update.component.html',
})
export class PaymentsFileTypeUpdateComponent implements OnInit {
  isSaving = false;
  placeholders: IPlaceholder[] = [];

  editForm = this.fb.group({
    id: [],
    paymentsFileTypeName: [null, [Validators.required]],
    paymentsFileMediumType: [null, [Validators.required]],
    description: [],
    fileTemplate: [],
    fileTemplateContentType: [],
    paymentsfileType: [],
    placeholders: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected paymentsFileTypeService: PaymentsFileTypeService,
    protected placeholderService: PlaceholderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentsFileType }) => {
      this.updateForm(paymentsFileType);

      this.placeholderService.query().subscribe((res: HttpResponse<IPlaceholder[]>) => (this.placeholders = res.body || []));
    });
  }

  updateForm(paymentsFileType: IPaymentsFileType): void {
    this.editForm.patchValue({
      id: paymentsFileType.id,
      paymentsFileTypeName: paymentsFileType.paymentsFileTypeName,
      paymentsFileMediumType: paymentsFileType.paymentsFileMediumType,
      description: paymentsFileType.description,
      fileTemplate: paymentsFileType.fileTemplate,
      fileTemplateContentType: paymentsFileType.fileTemplateContentType,
      paymentsfileType: paymentsFileType.paymentsfileType,
      placeholders: paymentsFileType.placeholders,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('paymentRecordsGateApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentsFileType = this.createFromForm();
    if (paymentsFileType.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentsFileTypeService.update(paymentsFileType));
    } else {
      this.subscribeToSaveResponse(this.paymentsFileTypeService.create(paymentsFileType));
    }
  }

  private createFromForm(): IPaymentsFileType {
    return {
      ...new PaymentsFileType(),
      id: this.editForm.get(['id'])!.value,
      paymentsFileTypeName: this.editForm.get(['paymentsFileTypeName'])!.value,
      paymentsFileMediumType: this.editForm.get(['paymentsFileMediumType'])!.value,
      description: this.editForm.get(['description'])!.value,
      fileTemplateContentType: this.editForm.get(['fileTemplateContentType'])!.value,
      fileTemplate: this.editForm.get(['fileTemplate'])!.value,
      paymentsfileType: this.editForm.get(['paymentsfileType'])!.value,
      placeholders: this.editForm.get(['placeholders'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentsFileType>>): void {
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

  trackById(index: number, item: IPlaceholder): any {
    return item.id;
  }

  getSelected(selectedVals: IPlaceholder[], option: IPlaceholder): IPlaceholder {
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
