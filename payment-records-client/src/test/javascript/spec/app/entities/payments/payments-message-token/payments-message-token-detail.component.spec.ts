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

import { PaymentRecordsClientTestModule } from '../../../../test.module';
import { PaymentsMessageTokenDetailComponent } from 'app/entities/payments/payments-message-token/payments-message-token-detail.component';
import { PaymentsMessageToken } from 'app/shared/model/payments/payments-message-token.model';

describe('Component Tests', () => {
  describe('PaymentsMessageToken Management Detail Component', () => {
    let comp: PaymentsMessageTokenDetailComponent;
    let fixture: ComponentFixture<PaymentsMessageTokenDetailComponent>;
    const route = ({ data: of({ paymentsMessageToken: new PaymentsMessageToken(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsClientTestModule],
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
