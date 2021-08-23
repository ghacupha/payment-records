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

import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PaymentRecordsClientTestModule } from '../../../../test.module';
import { PaymentsFileUploadUpdateComponent } from 'app/entities/payments/payments-file-upload/payments-file-upload-update.component';
import { PaymentsFileUploadService } from 'app/entities/payments/payments-file-upload/payments-file-upload.service';
import { PaymentsFileUpload } from 'app/shared/model/payments/payments-file-upload.model';

describe('Component Tests', () => {
  describe('PaymentsFileUpload Management Update Component', () => {
    let comp: PaymentsFileUploadUpdateComponent;
    let fixture: ComponentFixture<PaymentsFileUploadUpdateComponent>;
    let service: PaymentsFileUploadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsClientTestModule],
        declarations: [PaymentsFileUploadUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PaymentsFileUploadUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentsFileUploadUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentsFileUploadService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaymentsFileUpload(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaymentsFileUpload();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
