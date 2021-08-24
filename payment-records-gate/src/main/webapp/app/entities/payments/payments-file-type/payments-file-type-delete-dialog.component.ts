import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentsFileType } from 'app/shared/model/payments/payments-file-type.model';
import { PaymentsFileTypeService } from './payments-file-type.service';

@Component({
  templateUrl: './payments-file-type-delete-dialog.component.html',
})
export class PaymentsFileTypeDeleteDialogComponent {
  paymentsFileType?: IPaymentsFileType;

  constructor(
    protected paymentsFileTypeService: PaymentsFileTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentsFileTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paymentsFileTypeListModification');
      this.activeModal.close();
    });
  }
}
