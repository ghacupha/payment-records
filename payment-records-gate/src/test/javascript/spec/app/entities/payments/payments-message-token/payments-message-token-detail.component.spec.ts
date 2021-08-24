import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaymentRecordsGateTestModule } from '../../../../test.module';
import { PaymentsMessageTokenDetailComponent } from 'app/entities/payments/payments-message-token/payments-message-token-detail.component';
import { PaymentsMessageToken } from 'app/shared/model/payments/payments-message-token.model';

describe('Component Tests', () => {
  describe('PaymentsMessageToken Management Detail Component', () => {
    let comp: PaymentsMessageTokenDetailComponent;
    let fixture: ComponentFixture<PaymentsMessageTokenDetailComponent>;
    const route = ({ data: of({ paymentsMessageToken: new PaymentsMessageToken(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsGateTestModule],
        declarations: [PaymentsMessageTokenDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PaymentsMessageTokenDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentsMessageTokenDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load paymentsMessageToken on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paymentsMessageToken).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
