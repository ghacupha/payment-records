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
import { IPlaceholder, Placeholder } from 'app/shared/model/paymentRecords/placeholder.model';
import { PlaceholderService } from './placeholder.service';
import { PlaceholderComponent } from './placeholder.component';
import { PlaceholderDetailComponent } from './placeholder-detail.component';
import { PlaceholderUpdateComponent } from './placeholder-update.component';

@Injectable({ providedIn: 'root' })
export class PlaceholderResolve implements Resolve<IPlaceholder> {
  constructor(private service: PlaceholderService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPlaceholder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((placeholder: HttpResponse<Placeholder>) => {
          if (placeholder.body) {
            return of(placeholder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Placeholder());
  }
}

export const placeholderRoute: Routes = [
  {
    path: '',
    component: PlaceholderComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Placeholders',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlaceholderDetailComponent,
    resolve: {
      placeholder: PlaceholderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Placeholders',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlaceholderUpdateComponent,
    resolve: {
      placeholder: PlaceholderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Placeholders',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlaceholderUpdateComponent,
    resolve: {
      placeholder: PlaceholderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Placeholders',
    },
    canActivate: [UserRouteAccessService],
  },
];
