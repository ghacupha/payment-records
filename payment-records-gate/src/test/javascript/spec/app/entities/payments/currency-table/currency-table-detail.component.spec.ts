import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaymentRecordsGateTestModule } from '../../../../test.module';
import { CurrencyTableDetailComponent } from 'app/entities/payments/currency-table/currency-table-detail.component';
import { CurrencyTable } from 'app/shared/model/payments/currency-table.model';

describe('Component Tests', () => {
  describe('CurrencyTable Management Detail Component', () => {
    let comp: CurrencyTableDetailComponent;
    let fixture: ComponentFixture<CurrencyTableDetailComponent>;
    const route = ({ data: of({ currencyTable: new CurrencyTable(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsGateTestModule],
        declarations: [CurrencyTableDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(CurrencyTableDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CurrencyTableDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load currencyTable on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.currencyTable).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
