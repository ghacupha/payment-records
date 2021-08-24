import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PaymentRecordsGateTestModule } from '../../../../test.module';
import { MockEventManager } from '../../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../../helpers/mock-active-modal.service';
import { PaymentsFileUploadDeleteDialogComponent } from 'app/entities/payments/payments-file-upload/payments-file-upload-delete-dialog.component';
import { PaymentsFileUploadService } from 'app/entities/payments/payments-file-upload/payments-file-upload.service';

describe('Component Tests', () => {
  describe('PaymentsFileUpload Management Delete Component', () => {
    let comp: PaymentsFileUploadDeleteDialogComponent;
    let fixture: ComponentFixture<PaymentsFileUploadDeleteDialogComponent>;
    let service: PaymentsFileUploadService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsGateTestModule],
        declarations: [PaymentsFileUploadDeleteDialogComponent],
      })
        .overrideTemplate(PaymentsFileUploadDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PaymentsFileUploadDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PaymentsFileUploadService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
