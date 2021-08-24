import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentsFileUpload } from 'app/shared/model/payments/payments-file-upload.model';
import { PaymentsFileUploadService } from './payments-file-upload.service';

@Component({
  templateUrl: './payments-file-upload-delete-dialog.component.html',
})
export class PaymentsFileUploadDeleteDialogComponent {
  paymentsFileUpload?: IPaymentsFileUpload;

  constructor(
    protected paymentsFileUploadService: PaymentsFileUploadService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentsFileUploadService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paymentsFileUploadListModification');
      this.activeModal.close();
    });
  }
}
