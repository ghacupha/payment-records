import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PaymentRecordsGateTestModule } from '../../../../test.module';
import { CurrencyTableUpdateComponent } from 'app/entities/payments/currency-table/currency-table-update.component';
import { CurrencyTableService } from 'app/entities/payments/currency-table/currency-table.service';
import { CurrencyTable } from 'app/shared/model/payments/currency-table.model';

describe('Component Tests', () => {
  describe('CurrencyTable Management Update Component', () => {
    let comp: CurrencyTableUpdateComponent;
    let fixture: ComponentFixture<CurrencyTableUpdateComponent>;
    let service: CurrencyTableService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsGateTestModule],
        declarations: [CurrencyTableUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CurrencyTableUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CurrencyTableUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CurrencyTableService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CurrencyTable(123);
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
        const entity = new CurrencyTable();
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
