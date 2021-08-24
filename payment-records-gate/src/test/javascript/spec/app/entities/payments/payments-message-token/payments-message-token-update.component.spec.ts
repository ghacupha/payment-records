import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PaymentRecordsGateTestModule } from '../../../../test.module';
import { PaymentsMessageTokenUpdateComponent } from 'app/entities/payments/payments-message-token/payments-message-token-update.component';
import { PaymentsMessageTokenService } from 'app/entities/payments/payments-message-token/payments-message-token.service';
import { PaymentsMessageToken } from 'app/shared/model/payments/payments-message-token.model';

describe('Component Tests', () => {
  describe('PaymentsMessageToken Management Update Component', () => {
    let comp: PaymentsMessageTokenUpdateComponent;
    let fixture: ComponentFixture<PaymentsMessageTokenUpdateComponent>;
    let service: PaymentsMessageTokenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsGateTestModule],
        declarations: [PaymentsMessageTokenUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PaymentsMessageTokenUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentsMessageTokenUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentsMessageTokenService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PaymentsMessageToken(123);
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
        const entity = new PaymentsMessageToken();
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
