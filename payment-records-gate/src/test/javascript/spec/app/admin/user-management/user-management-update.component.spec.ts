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

import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Authority } from 'app/shared/constants/authority.constants';
import { PaymentRecordsGateTestModule } from '../../../test.module';
import { UserManagementUpdateComponent } from 'app/admin/user-management/user-management-update.component';
import { UserService } from 'app/core/user/user.service';
import { User } from 'app/core/user/user.model';

describe('Component Tests', () => {
  describe('User Management Update Component', () => {
    let comp: UserManagementUpdateComponent;
    let fixture: ComponentFixture<UserManagementUpdateComponent>;
    let service: UserService;
    const route: ActivatedRoute = ({
      data: of({ user: new User(1, 'user', 'first', 'last', 'first@last.com', true, 'en', [Authority.USER], 'admin') }),
    } as any) as ActivatedRoute;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsGateTestModule],
        declarations: [UserManagementUpdateComponent],
        providers: [
          FormBuilder,
          {
            provide: ActivatedRoute,
            useValue: route,
          },
        ],
      })
        .overrideTemplate(UserManagementUpdateComponent, '')
        .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(UserManagementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserService);
    });

    describe('OnInit', () => {
      it('Should load authorities and language on init', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'authorities').and.returnValue(of(['USER']));

          // WHEN
          comp.ngOnInit();

          // THEN
          expect(service.authorities).toHaveBeenCalled();
          expect(comp.authorities).toEqual(['USER']);
        })
      ));
    });

    describe('save', () => {
      it('Should call update service on save for existing user', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          const entity = new User(123);
          spyOn(service, 'update').and.returnValue(
            of(
              new HttpResponse({
                body: entity,
              })
            )
          );
          comp.user = entity;
          comp.editForm.patchValue({ id: entity.id });
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.update).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      ));

      it('Should call create service on save for new user', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          const entity = new User();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.user = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.create).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      ));
    });
  });
});
