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

import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { JhiAlertService } from 'ng-jhipster';

import { PaymentRecordsClientTestModule } from '../../../test.module';
import { AlertComponent } from 'app/shared/alert/alert.component';

describe('Component Tests', () => {
  describe('Alert Component', () => {
    let comp: AlertComponent;
    let fixture: ComponentFixture<AlertComponent>;
    let alertService: JhiAlertService;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsClientTestModule],
        declarations: [AlertComponent],
      })
        .overrideTemplate(AlertComponent, '')
        .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(AlertComponent);
      comp = fixture.componentInstance;
      alertService = TestBed.get(JhiAlertService);
    });

    it('Should call alertService.get on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(alertService.get).toHaveBeenCalled();
    });

    it('Should call alertService.clear on destroy', () => {
      // WHEN
      comp.ngOnDestroy();

      // THEN
      expect(alertService.clear).toHaveBeenCalled();
    });
  });
});
