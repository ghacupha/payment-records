import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPaymentsFileUpload, PaymentsFileUpload } from 'app/shared/model/payments/payments-file-upload.model';
import { PaymentsFileUploadService } from './payments-file-upload.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IPlaceholder } from 'app/shared/model/paymentRecords/placeholder.model';
import { PlaceholderService } from 'app/entities/paymentRecords/placeholder/placeholder.service';

@Component({
  selector: 'jhi-payments-file-upload-update',
  templateUrl: './payments-file-upload-update.component.html',
})
export class PaymentsFileUploadUpdateComponent implements OnInit {
  isSaving = false;
  placeholders: IPlaceholder[] = [];
  periodFromDp: any;
  periodToDp: any;

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    fileName: [null, [Validators.required]],
    periodFrom: [],
    periodTo: [],
    paymentsFileTypeId: [null, [Validators.required]],
    dataFile: [null, [Validators.required]],
    dataFileContentType: [],
    uploadSuccessful: [],
    uploadProcessed: [],
    uploadToken: [null, []],
    placeholders: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected paymentsFileUploadService: PaymentsFileUploadService,
    protected placeholderService: PlaceholderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentsFileUpload }) => {
      this.updateForm(paymentsFileUpload);

      this.placeholderService.query().subscribe((res: HttpResponse<IPlaceholder[]>) => (this.placeholders = res.body || []));
    });
  }

  updateForm(paymentsFileUpload: IPaymentsFileUpload): void {
    this.editForm.patchValue({
      id: paymentsFileUpload.id,
      description: paymentsFileUpload.description,
      fileName: paymentsFileUpload.fileName,
      periodFrom: paymentsFileUpload.periodFrom,
      periodTo: paymentsFileUpload.periodTo,
      paymentsFileTypeId: paymentsFileUpload.paymentsFileTypeId,
      dataFile: paymentsFileUpload.dataFile,
      dataFileContentType: paymentsFileUpload.dataFileContentType,
      uploadSuccessful: paymentsFileUpload.uploadSuccessful,
      uploadProcessed: paymentsFileUpload.uploadProcessed,
      uploadToken: paymentsFileUpload.uploadToken,
      placeholders: paymentsFileUpload.placeholders,
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
    const paymentsFileUpload = this.createFromForm();
    if (paymentsFileUpload.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentsFileUploadService.update(paymentsFileUpload));
    } else {
      this.subscribeToSaveResponse(this.paymentsFileUploadService.create(paymentsFileUpload));
    }
  }

  private createFromForm(): IPaymentsFileUpload {
    return {
      ...new PaymentsFileUpload(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      fileName: this.editForm.get(['fileName'])!.value,
      periodFrom: this.editForm.get(['periodFrom'])!.value,
      periodTo: this.editForm.get(['periodTo'])!.value,
      paymentsFileTypeId: this.editForm.get(['paymentsFileTypeId'])!.value,
      dataFileContentType: this.editForm.get(['dataFileContentType'])!.value,
      dataFile: this.editForm.get(['dataFile'])!.value,
      uploadSuccessful: this.editForm.get(['uploadSuccessful'])!.value,
      uploadProcessed: this.editForm.get(['uploadProcessed'])!.value,
      uploadToken: this.editForm.get(['uploadToken'])!.value,
      placeholders: this.editForm.get(['placeholders'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentsFileUpload>>): void {
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
