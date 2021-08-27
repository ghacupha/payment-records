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
import { IPaymentsMessageToken, PaymentsMessageToken } from 'app/shared/model/payments/payments-message-token.model';
import { PaymentsMessageTokenService } from './payments-message-token.service';
import { PaymentsMessageTokenComponent } from './payments-message-token.component';
import { PaymentsMessageTokenDetailComponent } from './payments-message-token-detail.component';
import { PaymentsMessageTokenUpdateComponent } from './payments-message-token-update.component';

@Injectable({ providedIn: 'root' })
export class PaymentsMessageTokenResolve implements Resolve<IPaymentsMessageToken> {
  constructor(private service: PaymentsMessageTokenService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentsMessageToken> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((paymentsMessageToken: HttpResponse<PaymentsMessageToken>) => {
          if (paymentsMessageToken.body) {
            return of(paymentsMessageToken.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PaymentsMessageToken());
  }
}

export const paymentsMessageTokenRoute: Routes = [
  {
    path: '',
    component: PaymentsMessageTokenComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'PaymentsMessageTokens',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentsMessageTokenDetailComponent,
    resolve: {
      paymentsMessageToken: PaymentsMessageTokenResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PaymentsMessageTokens',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentsMessageTokenUpdateComponent,
    resolve: {
      paymentsMessageToken: PaymentsMessageTokenResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PaymentsMessageTokens',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentsMessageTokenUpdateComponent,
    resolve: {
      paymentsMessageToken: PaymentsMessageTokenResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PaymentsMessageTokens',
    },
    canActivate: [UserRouteAccessService],
  },
];
