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
import { IPaymentsFileType, PaymentsFileType } from 'app/shared/model/payments/payments-file-type.model';
import { PaymentsFileTypeService } from './payments-file-type.service';
import { PaymentsFileTypeComponent } from './payments-file-type.component';
import { PaymentsFileTypeDetailComponent } from './payments-file-type-detail.component';
import { PaymentsFileTypeUpdateComponent } from './payments-file-type-update.component';

@Injectable({ providedIn: 'root' })
export class PaymentsFileTypeResolve implements Resolve<IPaymentsFileType> {
  constructor(private service: PaymentsFileTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentsFileType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((paymentsFileType: HttpResponse<PaymentsFileType>) => {
          if (paymentsFileType.body) {
            return of(paymentsFileType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PaymentsFileType());
  }
}

export const paymentsFileTypeRoute: Routes = [
  {
    path: '',
    component: PaymentsFileTypeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'PaymentsFileTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentsFileTypeDetailComponent,
    resolve: {
      paymentsFileType: PaymentsFileTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PaymentsFileTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentsFileTypeUpdateComponent,
    resolve: {
      paymentsFileType: PaymentsFileTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PaymentsFileTypes',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentsFileTypeUpdateComponent,
    resolve: {
      paymentsFileType: PaymentsFileTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PaymentsFileTypes',
    },
    canActivate: [UserRouteAccessService],
  },
];
