///
/// ERP System - ERP data management platform: Payment Records
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
import { HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { PaymentRecordsClientTestModule } from '../../../test.module';
import { AlertErrorComponent } from 'app/shared/alert/alert-error.component';
import { MockAlertService } from '../../../helpers/mock-alert.service';

describe('Component Tests', () => {
  describe('Alert Error Component', () => {
    let comp: AlertErrorComponent;
    let fixture: ComponentFixture<AlertErrorComponent>;
    let eventManager: JhiEventManager;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsClientTestModule],
        declarations: [AlertErrorComponent],
        providers: [
          JhiEventManager,
          {
            provide: JhiAlertService,
            useClass: MockAlertService,
          },
        ],
      })
        .overrideTemplate(AlertErrorComponent, '')
        .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(AlertErrorComponent);
      comp = fixture.componentInstance;
      eventManager = fixture.debugElement.injector.get(JhiEventManager);
    });

    describe('Error Handling', () => {
      it('Should display an alert on status 0', () => {
        // GIVEN
        eventManager.broadcast({ name: 'paymentRecordsClientApp.httpError', content: { status: 0 } });
        // THEN
        expect(comp.alerts.length).toBe(1);
        expect(comp.alerts[0].msg).toBe('Server not reachable');
      });

      it('Should display an alert on status 404', () => {
        // GIVEN
        eventManager.broadcast({ name: 'paymentRecordsClientApp.httpError', content: { status: 404 } });
        // THEN
        expect(comp.alerts.length).toBe(1);
        expect(comp.alerts[0].msg).toBe('Not found');
      });

      it('Should display an alert on generic error', () => {
        // GIVEN
        eventManager.broadcast({ name: 'paymentRecordsClientApp.httpError', content: { error: { message: 'Error Message' } } });
        eventManager.broadcast({ name: 'paymentRecordsClientApp.httpError', content: { error: 'Second Error Message' } });
        // THEN
        expect(comp.alerts.length).toBe(2);
        expect(comp.alerts[0].msg).toBe('Error Message');
        expect(comp.alerts[1].msg).toBe('Second Error Message');
      });

      it('Should display an alert on status 400 for generic error', () => {
        // GIVEN
        const response = new HttpErrorResponse({
          url: 'http://localhost:8080/api/foos',
          headers: new HttpHeaders(),
          status: 400,
          statusText: 'Bad Request',
          error: {
            type: 'https://www.jhipster.tech/problem/constraint-violation',
            title: 'Bad Request',
            status: 400,
            path: '/api/foos',
            message: 'error.validation',
          },
        });
        eventManager.broadcast({ name: 'paymentRecordsClientApp.httpError', content: response });
        // THEN
        expect(comp.alerts.length).toBe(1);
        expect(comp.alerts[0].msg).toBe('error.validation');
      });

      it('Should display an alert on status 400 for generic error without message', () => {
        // GIVEN
        const response = new HttpErrorResponse({
          url: 'http://localhost:8080/api/foos',
          headers: new HttpHeaders(),
          status: 400,
          error: 'Bad Request',
        });
        eventManager.broadcast({ name: 'paymentRecordsClientApp.httpError', content: response });
        // THEN
        expect(comp.alerts.length).toBe(1);
        expect(comp.alerts[0].msg).toBe('Bad Request');
      });

      it('Should display an alert on status 400 for invalid parameters', () => {
        // GIVEN
        const response = new HttpErrorResponse({
          url: 'http://localhost:8080/api/foos',
          headers: new HttpHeaders(),
          status: 400,
          statusText: 'Bad Request',
          error: {
            type: 'https://www.jhipster.tech/problem/constraint-violation',
            title: 'Method argument not valid',
            status: 400,
            path: '/api/foos',
            message: 'error.validation',
            fieldErrors: [{ objectName: 'foo', field: 'minField', message: 'Min' }],
          },
        });
        eventManager.broadcast({ name: 'paymentRecordsClientApp.httpError', content: response });
        // THEN
        expect(comp.alerts.length).toBe(1);
        expect(comp.alerts[0].msg).toBe('Error on field "MinField"');
      });

      it('Should display an alert on status 400 for error headers', () => {
        // GIVEN
        const response = new HttpErrorResponse({
          url: 'http://localhost:8080/api/foos',
          headers: new HttpHeaders().append('app-error', 'Error Message').append('app-params', 'foo'),
          status: 400,
          statusText: 'Bad Request',
          error: {
            status: 400,
            message: 'error.validation',
          },
        });
        eventManager.broadcast({ name: 'paymentRecordsClientApp.httpError', content: response });
        // THEN
        expect(comp.alerts.length).toBe(1);
        expect(comp.alerts[0].msg).toBe('Error Message');
      });
    });
  });
});
