import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { PaymentRecordsGateTestModule } from '../../../../test.module';
import { PaymentsFileTypeDetailComponent } from 'app/entities/payments/payments-file-type/payments-file-type-detail.component';
import { PaymentsFileType } from 'app/shared/model/payments/payments-file-type.model';

describe('Component Tests', () => {
  describe('PaymentsFileType Management Detail Component', () => {
    let comp: PaymentsFileTypeDetailComponent;
    let fixture: ComponentFixture<PaymentsFileTypeDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ paymentsFileType: new PaymentsFileType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsGateTestModule],
        declarations: [PaymentsFileTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PaymentsFileTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentsFileTypeDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load paymentsFileType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.paymentsFileType).toEqual(jasmine.objectContaining({ id: 123 }));
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
