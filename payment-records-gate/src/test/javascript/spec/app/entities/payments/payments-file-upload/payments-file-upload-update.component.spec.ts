import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PaymentRecordsGateTestModule } from '../../../../test.module';
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
        imports: [PaymentRecordsGateTestModule],
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
