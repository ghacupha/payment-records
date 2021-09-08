import {ComponentFixture, TestBed, fakeAsync, tick} from '@angular/core/testing';
import {HttpResponse} from '@angular/common/http';
import {FormBuilder} from '@angular/forms';
import {of} from 'rxjs';

import {PaymentRecordsGateTestModule} from '../../../../test.module';
import {PaymentUpdateComponent} from 'app/entities/paymentRecords/payment/payment-update.component';
import {PaymentService} from 'app/entities/paymentRecords/payment/payment.service';
import {Payment} from 'app/shared/model/paymentRecords/payment.model';
import {initialState} from "app/payment-records/store/global-store.definition";
import {MockStore, provideMockStore} from "@ngrx/store/testing";

describe('Component Tests', () => {
  describe('Payment Management Update Component', () => {
    let comp: PaymentUpdateComponent;
    let fixture: ComponentFixture<PaymentUpdateComponent>;
    let service: PaymentService;
    let store: MockStore;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsGateTestModule],
        declarations: [PaymentUpdateComponent],
        providers: [
          FormBuilder,
          provideMockStore({initialState})
        ],
      })
        .overrideTemplate(PaymentUpdateComponent, '')
        .compileComponents();

      store = TestBed.inject(MockStore);
      fixture = TestBed.createComponent(PaymentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentService);
    });

    describe('persistence', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Payment(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({body: entity})));
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
        const entity = new Payment();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({body: entity})));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });

    describe('payment actions', () => {
      it('Should call payment-save-button-clicked action on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Payment(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({body: entity})));
        spyOn(store, 'dispatch');
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(store.dispatch).toHaveBeenCalledWith({"type": "[Payment Update Form: Save Button] payment save button clicked"});
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call payment-copy-button-clicked action on copy for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Payment(123);
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({body: entity})));
        spyOn(store, 'dispatch');
        comp.updateForm(entity);
        // WHEN
        comp.copy();
        tick(); // simulate async

        // THEN
        expect(store.dispatch).toHaveBeenCalledWith({"type": "[Payment Update Form: Copy Button] payment copy button clicked"});
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call payment-update-button-clicked action on edit for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Payment(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({body: entity})));
        spyOn(store, 'dispatch');
        comp.updateForm(entity);
        // WHEN
        comp.edit();
        tick(); // simulate async

        // THEN
        expect(store.dispatch).toHaveBeenCalledWith({"type": "[Payment Edit Form: Update Button] payment update button clicked"});
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call payment-Update-Cancel-Button-Clicked action on cancel', fakeAsync(() => {
        // GIVEN
        spyOn(store, 'dispatch');

        // WHEN
        comp.previousState();
        tick(); // simulate async

        // THEN
        expect(store.dispatch).toHaveBeenCalledWith({"type": "[Payment Update Form: Cancel Button] payment update cancel button clicked"});
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call payment-Update-Error-Has-Occurred action on save-error', fakeAsync(() => {
        // GIVEN
        spyOn(store, 'dispatch');

        // WHEN
        comp.onSaveError();
        tick(); // simulate async

        // THEN
        expect(store.dispatch).toHaveBeenCalledWith({"type": "[Payment Update Form: Error] Payment update error encountered"});
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
