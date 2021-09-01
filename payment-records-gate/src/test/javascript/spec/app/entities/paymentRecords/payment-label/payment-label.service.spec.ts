import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PaymentLabelService } from 'app/entities/paymentRecords/payment-label/payment-label.service';
import { IPaymentLabel, PaymentLabel } from 'app/shared/model/paymentRecords/payment-label.model';

describe('Service Tests', () => {
  describe('PaymentLabel Service', () => {
    let injector: TestBed;
    let service: PaymentLabelService;
    let httpMock: HttpTestingController;
    let elemDefault: IPaymentLabel;
    let expectedResult: IPaymentLabel | IPaymentLabel[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PaymentLabelService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PaymentLabel(0, 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            schedule: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PaymentLabel', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            schedule: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            schedule: currentDate,
          },
          returnedFromService
        );

        service.create(new PaymentLabel()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PaymentLabel', () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            comments: 'BBBBBB',
            schedule: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            schedule: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PaymentLabel', () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            comments: 'BBBBBB',
            schedule: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            schedule: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PaymentLabel', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
