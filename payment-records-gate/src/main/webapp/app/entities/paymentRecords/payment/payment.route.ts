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

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPayment, Payment } from 'app/shared/model/paymentRecords/payment.model';
import { PaymentService } from './payment.service';
import { PaymentComponent } from './payment.component';
import { PaymentDetailComponent } from './payment-detail.component';
import { PaymentUpdateComponent } from './payment-update.component';
import {
  DEFAULT_CATEGORY,
  DEFAULT_CURRENCY,
  DEFAULT_DATE,
  DEFAULT_TRANSACTION_AMOUNT
} from "app/payment-records/default-values.constants";

@Injectable({ providedIn: 'root' })
export class PaymentResolve implements Resolve<IPayment> {
  constructor(private service: PaymentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPayment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((payment: HttpResponse<Payment>) => {
          if (payment.body) {
            return of(payment.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Payment());
  }
}

/**
 * Provides the New Payment form with default values
 */
@Injectable({ providedIn: 'root' })
export class NewPaymentResolve implements Resolve<IPayment> {

  resolve(route: ActivatedRouteSnapshot): Observable<IPayment> | Observable<never> {

    const payment: Payment = {
      paymentsCategory: DEFAULT_CATEGORY,
      transactionDate: DEFAULT_DATE,
      transactionCurrency: DEFAULT_CURRENCY,
      transactionAmount: DEFAULT_TRANSACTION_AMOUNT,
    }
    return of(payment);
  }
}

export const paymentRoute: Routes = [
  {
    path: '',
    component: PaymentComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Payments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentDetailComponent,
    resolve: {
      payment: PaymentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ERP| Payments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentUpdateComponent,
    resolve: {
      payment: NewPaymentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ERP| Payments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentUpdateComponent,
    resolve: {
      payment: PaymentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ERP| Payments',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/copy',
    component: PaymentUpdateComponent,
    resolve: {
      payment: PaymentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ERP| Copy Payment',
    },
    canActivate: [UserRouteAccessService],
  },
];
