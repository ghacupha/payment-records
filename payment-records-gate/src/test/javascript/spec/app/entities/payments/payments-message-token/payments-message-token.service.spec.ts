import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PaymentsMessageTokenService } from 'app/entities/payments/payments-message-token/payments-message-token.service';
import { IPaymentsMessageToken, PaymentsMessageToken } from 'app/shared/model/payments/payments-message-token.model';

describe('Service Tests', () => {
  describe('PaymentsMessageToken Service', () => {
    let injector: TestBed;
    let service: PaymentsMessageTokenService;
    let httpMock: HttpTestingController;
    let elemDefault: IPaymentsMessageToken;
    let expectedResult: IPaymentsMessageToken | IPaymentsMessageToken[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PaymentsMessageTokenService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PaymentsMessageToken(0, 'AAAAAAA', 0, 'AAAAAAA', false, false, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PaymentsMessageToken', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PaymentsMessageToken()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PaymentsMessageToken', () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            timeSent: 1,
            tokenValue: 'BBBBBB',
            received: true,
            actioned: true,
            contentFullyEnqueued: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PaymentsMessageToken', () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            timeSent: 1,
            tokenValue: 'BBBBBB',
            received: true,
            actioned: true,
            contentFullyEnqueued: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PaymentsMessageToken', () => {
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
