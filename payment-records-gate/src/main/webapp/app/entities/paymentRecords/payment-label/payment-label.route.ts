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
