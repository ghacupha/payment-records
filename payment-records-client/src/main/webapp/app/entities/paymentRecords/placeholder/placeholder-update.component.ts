///
/// Payment Records - Payment records is part of the ERP System
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
