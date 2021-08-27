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

import { Router } from '@angular/router';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { JhiDateUtils } from 'ng-jhipster';
import { NgxWebstorageModule } from 'ngx-webstorage';

import { SERVER_API_URL } from 'app/app.constants';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { Authority } from 'app/shared/constants/authority.constants';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { TrackerService } from 'app/core/tracker/tracker.service';
import { MockTrackerService } from '../../../helpers/mock-tracker.service';
import { MockRouter } from '../../../helpers/mock-route.service';
import { MockStateStorageService } from '../../../helpers/mock-state-storage.service';

function accountWithAuthorities(authorities: string[]): Account {
  return {
    activated: true,
    authorities,
    email: '',
    firstName: '',
    langKey: '',
    lastName: '',
    login: '',
    imageUrl: '',
  };
}

describe('Service Tests', () => {
  describe('Account Service', () => {
    let service: AccountService;
    let httpMock: HttpTestingController;
    let storageService: MockStateStorageService;
    let router: MockRouter;
    let trackerService: TrackerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule, NgxWebstorageModule.forRoot()],
        providers: [
          JhiDateUtils,
          {
            provide: TrackerService,
            useClass: MockTrackerService,
          },
          {
            provide: StateStorageService,
            useClass: MockStateStorageService,
          },
          {
            provide: Router,
            useClass: MockRouter,
          },
        ],
      });

      service = TestBed.get(AccountService);
      httpMock = TestBed.get(HttpTestingController);
      storageService = TestBed.get(StateStorageService);
      router = TestBed.get(Router);
      trackerService = TestBed.get(TrackerService);
    });

    afterEach(() => {
      httpMock.verify();
    });

    describe('authenticate', () => {
      it('authenticationState should emit null if input is null', () => {
        // GIVEN
        let userIdentity: Account | null = accountWithAuthorities([]);
        service.getAuthenticationState().subscribe(account => (userIdentity = account));

        // WHEN
        service.authenticate(null);

        // THEN
        expect(userIdentity).toBeNull();
        expect(service.isAuthenticated()).toBe(false);
        expect(trackerService.disconnect).toHaveBeenCalled();
        expect(trackerService.connect).not.toHaveBeenCalled();
      });

      it('authenticationState should emit the same account as was in input parameter', () => {
        // GIVEN
        const expectedResult = accountWithAuthorities([]);
        let userIdentity: Account | null = null;
        service.getAuthenticationState().subscribe(account => (userIdentity = account));

        // WHEN
        service.authenticate(expectedResult);

        // THEN
        expect(userIdentity).toEqual(expectedResult);
        expect(service.isAuthenticated()).toBe(true);
        expect(trackerService.connect).toHaveBeenCalled();
        expect(trackerService.disconnect).not.toHaveBeenCalled();
      });
    });

    describe('identity', () => {
      it('should call /account if user is undefined', () => {
        service.identity().subscribe();
        const req = httpMock.expectOne({ method: 'GET' });
        const resourceUrl = SERVER_API_URL + 'api/account';

        expect(req.request.url).toEqual(`${resourceUrl}`);
      });

      it('should call /account only once if not logged out after first authentication and should call /account again if user has logged out', () => {
        // Given the user is authenticated
        service.identity().subscribe();
        httpMock.expectOne({ method: 'GET' }).flush({});

        // When I call
        service.identity().subscribe();

        // Then there is no second request
        httpMock.expectNone({ method: 'GET' });

        // When I log out
        service.authenticate(null);
        // and then call
        service.identity().subscribe();

        // Then there is a new request
        httpMock.expectOne({ method: 'GET' });
      });

      describe('navigateToStoredUrl', () => {
        it('should navigate to the previous stored url post successful authentication', () => {
          // GIVEN
          storageService.setResponse('admin/users?page=0');

          // WHEN
          service.identity().subscribe();
          httpMock.expectOne({ method: 'GET' }).flush({});

          // THEN
          expect(storageService.getUrlSpy).toHaveBeenCalledTimes(1);
          expect(storageService.clearUrlSpy).toHaveBeenCalledTimes(1);
          expect(router.navigateByUrlSpy).toHaveBeenCalledWith('admin/users?page=0');
        });

        it('should not navigate to the previous stored url when authentication fails', () => {
          // WHEN
          service.identity().subscribe();
          httpMock.expectOne({ method: 'GET' }).error(new ErrorEvent(''));

          // THEN
          expect(storageService.getUrlSpy).not.toHaveBeenCalled();
          expect(storageService.clearUrlSpy).not.toHaveBeenCalled();
          expect(router.navigateByUrlSpy).not.toHaveBeenCalled();
        });

        it('should not navigate to the previous stored url when no such url exists post successful authentication', () => {
          // GIVEN
          storageService.setResponse(null);

          // WHEN
          service.identity().subscribe();
          httpMock.expectOne({ method: 'GET' }).flush({});

          // THEN
          expect(storageService.getUrlSpy).toHaveBeenCalledTimes(1);
          expect(storageService.clearUrlSpy).not.toHaveBeenCalled();
          expect(router.navigateByUrlSpy).not.toHaveBeenCalled();
        });
      });
    });

    describe('hasAnyAuthority', () => {
      describe('hasAnyAuthority string parameter', () => {
        it('should return false if user is not logged', () => {
          const hasAuthority = service.hasAnyAuthority(Authority.USER);
          expect(hasAuthority).toBe(false);
        });

        it('should return false if user is logged and has not authority', () => {
          service.authenticate(accountWithAuthorities([Authority.USER]));

          const hasAuthority = service.hasAnyAuthority(Authority.ADMIN);

          expect(hasAuthority).toBe(false);
        });

        it('should return true if user is logged and has authority', () => {
          service.authenticate(accountWithAuthorities([Authority.USER]));

          const hasAuthority = service.hasAnyAuthority(Authority.USER);

          expect(hasAuthority).toBe(true);
        });
      });

      describe('hasAnyAuthority array parameter', () => {
        it('should return false if user is not logged', () => {
          const hasAuthority = service.hasAnyAuthority([Authority.USER]);
          expect(hasAuthority).toBeFalsy();
        });

        it('should return false if user is logged and has not authority', () => {
          service.authenticate(accountWithAuthorities([Authority.USER]));

          const hasAuthority = service.hasAnyAuthority([Authority.ADMIN]);

          expect(hasAuthority).toBe(false);
        });

        it('should return true if user is logged and has authority', () => {
          service.authenticate(accountWithAuthorities([Authority.USER]));

          const hasAuthority = service.hasAnyAuthority([Authority.USER, Authority.ADMIN]);

          expect(hasAuthority).toBe(true);
        });
      });
    });
  });
});
