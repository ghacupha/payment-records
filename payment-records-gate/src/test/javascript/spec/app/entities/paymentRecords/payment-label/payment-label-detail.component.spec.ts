import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaymentRecordsGateTestModule } from '../../../../test.module';
import { PaymentLabelDetailComponent } from 'app/entities/paymentRecords/payment-label/payment-label-detail.component';
import { PaymentLabel } from 'app/shared/model/paymentRecords/payment-label.model';

describe('Component Tests', () => {
  describe('PaymentLabel Management Detail Component', () => {
    let comp: PaymentLabelDetailComponent;
    let fixture: ComponentFixture<PaymentLabelDetailComponent>;
    const route = ({ data: of({ paymentLabel: new PaymentLabel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsGateTestModule],
        declarations: [PaymentLabelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PaymentLabelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentLabelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load paymentLabel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paymentLabel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
