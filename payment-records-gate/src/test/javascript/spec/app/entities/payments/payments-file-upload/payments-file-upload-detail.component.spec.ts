import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { PaymentRecordsGateTestModule } from '../../../../test.module';
import { PaymentsFileUploadDetailComponent } from 'app/entities/payments/payments-file-upload/payments-file-upload-detail.component';
import { PaymentsFileUpload } from 'app/shared/model/payments/payments-file-upload.model';

describe('Component Tests', () => {
  describe('PaymentsFileUpload Management Detail Component', () => {
    let comp: PaymentsFileUploadDetailComponent;
    let fixture: ComponentFixture<PaymentsFileUploadDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ paymentsFileUpload: new PaymentsFileUpload(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsGateTestModule],
        declarations: [PaymentsFileUploadDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PaymentsFileUploadDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentsFileUploadDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load paymentsFileUpload on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paymentsFileUpload).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
