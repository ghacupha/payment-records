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

import { ComponentFixture, TestBed, async, inject, tick, fakeAsync } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { of, throwError } from 'rxjs';

import { PaymentRecordsClientTestModule } from '../../../test.module';
import { EMAIL_ALREADY_USED_TYPE, LOGIN_ALREADY_USED_TYPE } from 'app/shared/constants/error.constants';
import { RegisterService } from 'app/account/register/register.service';
import { RegisterComponent } from 'app/account/register/register.component';

describe('Component Tests', () => {
  describe('RegisterComponent', () => {
    let fixture: ComponentFixture<RegisterComponent>;
    let comp: RegisterComponent;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsClientTestModule],
        declarations: [RegisterComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RegisterComponent, '')
        .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(RegisterComponent);
      comp = fixture.componentInstance;
    });

    it('should ensure the two passwords entered match', () => {
      comp.registerForm.patchValue({
        password: 'password',
        confirmPassword: 'non-matching',
      });

      comp.register();

      expect(comp.doNotMatch).toBe(true);
    });

    it('should update success to true after creating an account', inject(
      [RegisterService],
      fakeAsync((service: RegisterService) => {
        spyOn(service, 'save').and.returnValue(of({}));
        comp.registerForm.patchValue({
          password: 'password',
          confirmPassword: 'password',
        });

        comp.register();
        tick();

        expect(service.save).toHaveBeenCalledWith({
          email: '',
          password: 'password',
          login: '',
          langKey: 'en',
        });
        expect(comp.success).toBe(true);
        expect(comp.errorUserExists).toBe(false);
        expect(comp.errorEmailExists).toBe(false);
        expect(comp.error).toBe(false);
      })
    ));

    it('should notify of user existence upon 400/login already in use', inject(
      [RegisterService],
      fakeAsync((service: RegisterService) => {
        spyOn(service, 'save').and.returnValue(
          throwError({
            status: 400,
            error: { type: LOGIN_ALREADY_USED_TYPE },
          })
        );
        comp.registerForm.patchValue({
          password: 'password',
          confirmPassword: 'password',
        });

        comp.register();
        tick();

        expect(comp.errorUserExists).toBe(true);
        expect(comp.errorEmailExists).toBe(false);
        expect(comp.error).toBe(false);
      })
    ));

    it('should notify of email existence upon 400/email address already in use', inject(
      [RegisterService],
      fakeAsync((service: RegisterService) => {
        spyOn(service, 'save').and.returnValue(
          throwError({
            status: 400,
            error: { type: EMAIL_ALREADY_USED_TYPE },
          })
        );
        comp.registerForm.patchValue({
          password: 'password',
          confirmPassword: 'password',
        });

        comp.register();
        tick();

        expect(comp.errorEmailExists).toBe(true);
        expect(comp.errorUserExists).toBe(false);
        expect(comp.error).toBe(false);
      })
    ));

    it('should notify of generic error', inject(
      [RegisterService],
      fakeAsync((service: RegisterService) => {
        spyOn(service, 'save').and.returnValue(
          throwError({
            status: 503,
          })
        );
        comp.registerForm.patchValue({
          password: 'password',
          confirmPassword: 'password',
        });

        comp.register();
        tick();

        expect(comp.errorUserExists).toBe(false);
        expect(comp.errorEmailExists).toBe(false);
        expect(comp.error).toBe(true);
      })
    ));
  });
});
