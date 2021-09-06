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
import {PaymentUpdateFormStateService} from "app/payment-records/state/payment-update-form-state.service";
import {Store} from "@ngrx/store";
import {State} from "app/payment-records/store/global-store.definition";
import {
  newPaymentButtonClicked,
  paymentCopyInitiated,
  paymentEditInitiated
} from "app/payment-records/store/update-menu-status.actions";

/**
 * Provides the edit form containing the entity to be edited pre-filled
 */
@Injectable({ providedIn: 'root' })
export class ViewPaymentResolve implements Resolve<IPayment> {
  constructor(private service: PaymentService, private router: Router, private formState: PaymentUpdateFormStateService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPayment> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((payment: HttpResponse<Payment>) => {
          if (payment.body) {
            this.formState.paymentSelected(payment.body);
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
 * Provides the edit form containing the entity to be edited pre-filled
 */
@Injectable({ providedIn: 'root' })
export class EditPaymentResolve implements Resolve<IPayment> {
  constructor(
    private service: PaymentService,
    private router: Router,
    private formState: PaymentUpdateFormStateService,
    protected store: Store<State>
  ) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPayment> | Observable<never> {

    this.formState.paymentEdited();

    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((payment: HttpResponse<Payment>) => {
          if (payment.body) {
            this.store.dispatch(paymentEditInitiated({editPayment: payment.body}))
            //    this.formState.paymentSelected(payment.body);
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

  constructor(
    private formState: PaymentUpdateFormStateService,
    protected store: Store<State>
    ) {
  }


  /* eslint-disable-next-line */
  resolve(route: ActivatedRouteSnapshot): Observable<IPayment> | Observable<never> {

    //    this.formState.paymentCreated();
    //    this.formState.paymentSelected({...new Payment()});

    const payment: Payment = {
      paymentsCategory: DEFAULT_CATEGORY,
      transactionDate: DEFAULT_DATE,
      transactionCurrency: DEFAULT_CURRENCY,
      transactionAmount: DEFAULT_TRANSACTION_AMOUNT,
    }

    this.store.dispatch(newPaymentButtonClicked({newPayment: payment}))

    return of(payment);
  }
}

/**
 * Provides the update form containing the entity to be copied
 */
@Injectable({ providedIn: 'root' })
export class CopyPaymentResolve implements Resolve<IPayment> {
  constructor(
    private service: PaymentService,
    private router: Router,
    private formState: PaymentUpdateFormStateService,
    protected store: Store<State>
  ) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPayment> | Observable<never> {

    //  this.formState.paymentCopied();

    const id = route.params['id'];

    if (id) {
      return this.service.find(id).pipe(
        flatMap((payment: HttpResponse<Payment>) => {
          if (payment.body) {
            //  this.formState.paymentSelected(payment.body);
            this.store.dispatch(paymentCopyInitiated({copiedPayment: payment.body}))
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
      payment: ViewPaymentResolve,
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
      payment: EditPaymentResolve,
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
      payment: CopyPaymentResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ERP| Copy Payment',
    },
    canActivate: [UserRouteAccessService],
  },
];
