import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlaceholder } from 'app/shared/model/paymentRecords/placeholder.model';
import { PlaceholderService } from './placeholder.service';

@Component({
  templateUrl: './placeholder-delete-dialog.component.html',
})
export class PlaceholderDeleteDialogComponent {
  placeholder?: IPlaceholder;

  constructor(
    protected placeholderService: PlaceholderService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.placeholderService.delete(id).subscribe(() => {
      this.eventManager.broadcast('placeholderListModification');
      this.activeModal.close();
    });
  }
}
