import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PaymentRecordsGateTestModule } from '../../../../test.module';
import { PaymentLabelUpdateComponent } from 'app/entities/paymentRecords/payment-label/payment-label-update.component';
import { PaymentLabelService } from 'app/entities/paymentRecords/payment-label/payment-label.service';
import { PaymentLabel } from 'app/shared/model/paymentRecords/payment-label.model';

describe('Component Tests', () => {
  describe('PaymentLabel Management Update Component', () => {
    let comp: PaymentLabelUpdateComponent;
    let fixture: ComponentFixture<PaymentLabelUpdateComponent>;
    let service: PaymentLabelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsGateTestModule],
        declarations: [PaymentLabelUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PaymentLabelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentLabelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentLabelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaymentLabel(123);
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
        const entity = new PaymentLabel();
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
