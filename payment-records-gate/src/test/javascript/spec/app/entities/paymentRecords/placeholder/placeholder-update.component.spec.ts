import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PaymentRecordsGateTestModule } from '../../../../test.module';
import { PlaceholderUpdateComponent } from 'app/entities/paymentRecords/placeholder/placeholder-update.component';
import { PlaceholderService } from 'app/entities/paymentRecords/placeholder/placeholder.service';
import { Placeholder } from 'app/shared/model/paymentRecords/placeholder.model';

describe('Component Tests', () => {
  describe('Placeholder Management Update Component', () => {
    let comp: PlaceholderUpdateComponent;
    let fixture: ComponentFixture<PlaceholderUpdateComponent>;
    let service: PlaceholderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsGateTestModule],
        declarations: [PlaceholderUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PlaceholderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlaceholderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlaceholderService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Placeholder(123);
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
        const entity = new Placeholder();
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
