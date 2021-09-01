import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentLabel } from 'app/shared/model/paymentRecords/payment-label.model';
import { PaymentLabelService } from './payment-label.service';

@Component({
  templateUrl: './payment-label-delete-dialog.component.html',
})
export class PaymentLabelDeleteDialogComponent {
  paymentLabel?: IPaymentLabel;

  constructor(
    protected paymentLabelService: PaymentLabelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentLabelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paymentLabelListModification');
      this.activeModal.close();
    });
  }
}
