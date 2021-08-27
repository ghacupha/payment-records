///
/// Payment Records - Payment records is part of the ERP System
/// Copyright © 2021 Edwin Njeru (mailnjeru@gmail.com)
///
/// This program is free software: you can redistribute it and/or modify
/// it under the terms of the GNU General Public License as published by
/// the Free Software Foundation, either version 3 of the License, or
/// (at your option) any later version.
///
/// This program is distributed in the hope that it will be useful,
/// but WITHOUT ANY WARRANTY; without even the implied warranty of
/// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
/// GNU General Public License for more details.
///
/// You should have received a copy of the GNU General Public License
/// along with this program. If not, see <http://www.gnu.org/licenses/>.
///

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
