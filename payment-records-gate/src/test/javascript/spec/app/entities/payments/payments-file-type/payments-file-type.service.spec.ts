import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PaymentsFileTypeService } from 'app/entities/payments/payments-file-type/payments-file-type.service';
import { IPaymentsFileType, PaymentsFileType } from 'app/shared/model/payments/payments-file-type.model';
import { PaymentsFileMediumTypes } from 'app/shared/model/enumerations/payments-file-medium-types.model';
import { PaymentsFileModelType } from 'app/shared/model/enumerations/payments-file-model-type.model';

describe('Service Tests', () => {
  describe('PaymentsFileType Service', () => {
    let injector: TestBed;
    let service: PaymentsFileTypeService;
    let httpMock: HttpTestingController;
    let elemDefault: IPaymentsFileType;
    let expectedResult: IPaymentsFileType | IPaymentsFileType[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PaymentsFileTypeService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new PaymentsFileType(
        0,
        'AAAAAAA',
        PaymentsFileMediumTypes.EXCEL,
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        PaymentsFileModelType.CURRENCY_LIST
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PaymentsFileType', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new PaymentsFileType()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PaymentsFileType', () => {
        const returnedFromService = Object.assign(
          {
            paymentsFileTypeName: 'BBBBBB',
            paymentsFileMediumType: 'BBBBBB',
            description: 'BBBBBB',
            fileTemplate: 'BBBBBB',
            paymentsfileType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PaymentsFileType', () => {
        const returnedFromService = Object.assign(
          {
            paymentsFileTypeName: 'BBBBBB',
            paymentsFileMediumType: 'BBBBBB',
            description: 'BBBBBB',
            fileTemplate: 'BBBBBB',
            paymentsfileType: 'BBBBBB',
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

      it('should delete a PaymentsFileType', () => {
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
