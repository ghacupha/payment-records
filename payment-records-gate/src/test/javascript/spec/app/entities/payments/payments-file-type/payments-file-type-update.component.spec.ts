import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PaymentRecordsGateTestModule } from '../../../../test.module';
import { PaymentsFileTypeUpdateComponent } from 'app/entities/payments/payments-file-type/payments-file-type-update.component';
import { PaymentsFileTypeService } from 'app/entities/payments/payments-file-type/payments-file-type.service';
import { PaymentsFileType } from 'app/shared/model/payments/payments-file-type.model';

describe('Component Tests', () => {
  describe('PaymentsFileType Management Update Component', () => {
    let comp: PaymentsFileTypeUpdateComponent;
    let fixture: ComponentFixture<PaymentsFileTypeUpdateComponent>;
    let service: PaymentsFileTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsGateTestModule],
        declarations: [PaymentsFileTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PaymentsFileTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentsFileTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentsFileTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaymentsFileType(123);
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
        const entity = new PaymentsFileType();
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
