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
import { IPaymentsFileUpload, PaymentsFileUpload } from 'app/shared/model/payments/payments-file-upload.model';
import { PaymentsFileUploadService } from './payments-file-upload.service';
import { PaymentsFileUploadComponent } from './payments-file-upload.component';
import { PaymentsFileUploadDetailComponent } from './payments-file-upload-detail.component';
import { PaymentsFileUploadUpdateComponent } from './payments-file-upload-update.component';

@Injectable({ providedIn: 'root' })
export class PaymentsFileUploadResolve implements Resolve<IPaymentsFileUpload> {
  constructor(private service: PaymentsFileUploadService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentsFileUpload> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((paymentsFileUpload: HttpResponse<PaymentsFileUpload>) => {
          if (paymentsFileUpload.body) {
            return of(paymentsFileUpload.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PaymentsFileUpload());
  }
}

export const paymentsFileUploadRoute: Routes = [
  {
    path: '',
    component: PaymentsFileUploadComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'PaymentsFileUploads',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentsFileUploadDetailComponent,
    resolve: {
      paymentsFileUpload: PaymentsFileUploadResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PaymentsFileUploads',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentsFileUploadUpdateComponent,
    resolve: {
      paymentsFileUpload: PaymentsFileUploadResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PaymentsFileUploads',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentsFileUploadUpdateComponent,
    resolve: {
      paymentsFileUpload: PaymentsFileUploadResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PaymentsFileUploads',
    },
    canActivate: [UserRouteAccessService],
  },
];
