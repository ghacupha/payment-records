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
import { PlaceholderDetailComponent } from 'app/entities/paymentRecords/placeholder/placeholder-detail.component';
import { Placeholder } from 'app/shared/model/paymentRecords/placeholder.model';

describe('Component Tests', () => {
  describe('Placeholder Management Detail Component', () => {
    let comp: PlaceholderDetailComponent;
    let fixture: ComponentFixture<PlaceholderDetailComponent>;
    const route = ({ data: of({ placeholder: new Placeholder(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsClientTestModule],
        declarations: [PlaceholderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PlaceholderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlaceholderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load placeholder on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.placeholder).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
