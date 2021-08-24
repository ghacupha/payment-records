import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPaymentsFileType } from 'app/shared/model/payments/payments-file-type.model';

@Component({
  selector: 'jhi-payments-file-type-detail',
  templateUrl: './payments-file-type-detail.component.html',
})
export class PaymentsFileTypeDetailComponent implements OnInit {
  paymentsFileType: IPaymentsFileType | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentsFileType }) => (this.paymentsFileType = paymentsFileType));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
