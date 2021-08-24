import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentsMessageToken } from 'app/shared/model/payments/payments-message-token.model';

@Component({
  selector: 'jhi-payments-message-token-detail',
  templateUrl: './payments-message-token-detail.component.html',
})
export class PaymentsMessageTokenDetailComponent implements OnInit {
  paymentsMessageToken: IPaymentsMessageToken | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentsMessageToken }) => (this.paymentsMessageToken = paymentsMessageToken));
  }

  previousState(): void {
    window.history.back();
  }
}
