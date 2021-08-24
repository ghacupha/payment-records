import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { PaymentRecordsGateTestModule } from '../../../../test.module';
import { PaymentsFileTypeComponent } from 'app/entities/payments/payments-file-type/payments-file-type.component';
import { PaymentsFileTypeService } from 'app/entities/payments/payments-file-type/payments-file-type.service';
import { PaymentsFileType } from 'app/shared/model/payments/payments-file-type.model';

describe('Component Tests', () => {
  describe('PaymentsFileType Management Component', () => {
    let comp: PaymentsFileTypeComponent;
    let fixture: ComponentFixture<PaymentsFileTypeComponent>;
    let service: PaymentsFileTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsGateTestModule],
        declarations: [PaymentsFileTypeComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(PaymentsFileTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentsFileTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentsFileTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PaymentsFileType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.paymentsFileTypes && comp.paymentsFileTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PaymentsFileType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.paymentsFileTypes && comp.paymentsFileTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
