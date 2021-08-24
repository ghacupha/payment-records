import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPlaceholder, Placeholder } from 'app/shared/model/paymentRecords/placeholder.model';
import { PlaceholderService } from './placeholder.service';

@Component({
  selector: 'jhi-placeholder-update',
  templateUrl: './placeholder-update.component.html',
})
export class PlaceholderUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    token: [null, []],
  });

  constructor(protected placeholderService: PlaceholderService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ placeholder }) => {
      this.updateForm(placeholder);
    });
  }

  updateForm(placeholder: IPlaceholder): void {
    this.editForm.patchValue({
      id: placeholder.id,
      description: placeholder.description,
      token: placeholder.token,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const placeholder = this.createFromForm();
    if (placeholder.id !== undefined) {
      this.subscribeToSaveResponse(this.placeholderService.update(placeholder));
    } else {
      this.subscribeToSaveResponse(this.placeholderService.create(placeholder));
    }
  }

  private createFromForm(): IPlaceholder {
    return {
      ...new Placeholder(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      token: this.editForm.get(['token'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlaceholder>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
