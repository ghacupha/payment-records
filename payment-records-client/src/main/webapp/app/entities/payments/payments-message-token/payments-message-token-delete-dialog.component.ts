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
