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
