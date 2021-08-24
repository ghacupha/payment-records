import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICurrencyTable, CurrencyTable } from 'app/shared/model/payments/currency-table.model';
import { CurrencyTableService } from './currency-table.service';
import { IPlaceholder } from 'app/shared/model/paymentRecords/placeholder.model';
import { PlaceholderService } from 'app/entities/paymentRecords/placeholder/placeholder.service';

@Component({
  selector: 'jhi-currency-table-update',
  templateUrl: './currency-table-update.component.html',
})
export class CurrencyTableUpdateComponent implements OnInit {
  isSaving = false;
  placeholders: IPlaceholder[] = [];

  editForm = this.fb.group({
    id: [],
    currencyCode: [null, [Validators.minLength(3), Validators.maxLength(3)]],
    locality: [null, [Validators.required]],
    currencyName: [],
    country: [],
    placeholders: [],
  });

  constructor(
    protected currencyTableService: CurrencyTableService,
    protected placeholderService: PlaceholderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ currencyTable }) => {
      this.updateForm(currencyTable);

      this.placeholderService.query().subscribe((res: HttpResponse<IPlaceholder[]>) => (this.placeholders = res.body || []));
    });
  }

  updateForm(currencyTable: ICurrencyTable): void {
    this.editForm.patchValue({
      id: currencyTable.id,
      currencyCode: currencyTable.currencyCode,
      locality: currencyTable.locality,
      currencyName: currencyTable.currencyName,
      country: currencyTable.country,
      placeholders: currencyTable.placeholders,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const currencyTable = this.createFromForm();
    if (currencyTable.id !== undefined) {
      this.subscribeToSaveResponse(this.currencyTableService.update(currencyTable));
    } else {
      this.subscribeToSaveResponse(this.currencyTableService.create(currencyTable));
    }
  }

  private createFromForm(): ICurrencyTable {
    return {
      ...new CurrencyTable(),
      id: this.editForm.get(['id'])!.value,
      currencyCode: this.editForm.get(['currencyCode'])!.value,
      locality: this.editForm.get(['locality'])!.value,
      currencyName: this.editForm.get(['currencyName'])!.value,
      country: this.editForm.get(['country'])!.value,
      placeholders: this.editForm.get(['placeholders'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICurrencyTable>>): void {
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

  trackById(index: number, item: IPlaceholder): any {
    return item.id;
  }

  getSelected(selectedVals: IPlaceholder[], option: IPlaceholder): IPlaceholder {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
