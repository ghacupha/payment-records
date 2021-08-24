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
