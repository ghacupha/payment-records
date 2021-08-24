import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PaymentRecordsGateTestModule } from '../../../../test.module';
import { MockEventManager } from '../../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../../helpers/mock-active-modal.service';
import { PlaceholderDeleteDialogComponent } from 'app/entities/paymentRecords/placeholder/placeholder-delete-dialog.component';
import { PlaceholderService } from 'app/entities/paymentRecords/placeholder/placeholder.service';

describe('Component Tests', () => {
  describe('Placeholder Management Delete Component', () => {
    let comp: PlaceholderDeleteDialogComponent;
    let fixture: ComponentFixture<PlaceholderDeleteDialogComponent>;
    let service: PlaceholderService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PaymentRecordsGateTestModule],
        declarations: [PlaceholderDeleteDialogComponent],
      })
        .overrideTemplate(PlaceholderDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlaceholderDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlaceholderService);
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
