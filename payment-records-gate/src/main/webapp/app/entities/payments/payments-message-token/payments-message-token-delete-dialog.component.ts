import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPaymentsMessageToken } from 'app/shared/model/payments/payments-message-token.model';
import { PaymentsMessageTokenService } from './payments-message-token.service';

@Component({
  templateUrl: './payments-message-token-delete-dialog.component.html',
})
export class PaymentsMessageTokenDeleteDialogComponent {
  paymentsMessageToken?: IPaymentsMessageToken;

  constructor(
    protected paymentsMessageTokenService: PaymentsMessageTokenService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentsMessageTokenService.delete(id).subscribe(() => {
      this.eventManager.broadcast('paymentsMessageTokenListModification');
      this.activeModal.close();
    });
  }
}
