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
import { IPaymentLabel, PaymentLabel } from 'app/shared/model/paymentRecords/payment-label.model';
import { PaymentLabelService } from './payment-label.service';
import { PaymentLabelComponent } from './payment-label.component';
import { PaymentLabelDetailComponent } from './payment-label-detail.component';
import { PaymentLabelUpdateComponent } from './payment-label-update.component';

@Injectable({ providedIn: 'root' })
export class PaymentLabelResolve implements Resolve<IPaymentLabel> {
  constructor(private service: PaymentLabelService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentLabel> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((paymentLabel: HttpResponse<PaymentLabel>) => {
          if (paymentLabel.body) {
            return of(paymentLabel.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PaymentLabel());
  }
}

export const paymentLabelRoute: Routes = [
  {
    path: '',
    component: PaymentLabelComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'PaymentLabels',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentLabelDetailComponent,
    resolve: {
      paymentLabel: PaymentLabelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PaymentLabels',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentLabelUpdateComponent,
    resolve: {
      paymentLabel: PaymentLabelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PaymentLabels',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentLabelUpdateComponent,
    resolve: {
      paymentLabel: PaymentLabelResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PaymentLabels',
    },
    canActivate: [UserRouteAccessService],
  },
];
