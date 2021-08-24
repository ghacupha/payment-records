import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { PaymentRecordsGateTestModule } from '../../../../test.module';
import { PaymentsMessageTokenComponent } from 'app/entities/payments/payments-message-token/payments-message-token.component';
import { PaymentsMessageTokenService } from 'app/entities/payments/payments-message-token/payments-message-token.service';
import { PaymentsMessageToken } from 'app/shared/model/payments/payments-message-token.model';

describe('Component Tests', () => {
  describe('PaymentsMessageToken Management Component', () => {
    let comp: PaymentsMessageTokenComponent;
    let fixture: ComponentFixture<PaymentsMessageTokenComponent>;
    let service: PaymentsMessageTokenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsGateTestModule],
        declarations: [PaymentsMessageTokenComponent],
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
        .overrideTemplate(PaymentsMessageTokenComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PaymentsMessageTokenComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentsMessageTokenService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PaymentsMessageToken(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.paymentsMessageTokens && comp.paymentsMessageTokens[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PaymentsMessageToken(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.paymentsMessageTokens && comp.paymentsMessageTokens[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
