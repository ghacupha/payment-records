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

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IPaymentsFileUpload } from 'app/shared/model/payments/payments-file-upload.model';

type EntityResponseType = HttpResponse<IPaymentsFileUpload>;
type EntityArrayResponseType = HttpResponse<IPaymentsFileUpload[]>;

@Injectable({ providedIn: 'root' })
export class PaymentsFileUploadService {
  public resourceUrl = SERVER_API_URL + 'services/paymentrecords/api/app/file-uploads';
  public resourceSearchUrl = SERVER_API_URL + 'services/paymentrecords/api/_search/payments-file-uploads';

  constructor(protected http: HttpClient) {}

  create(paymentsFileUpload: IPaymentsFileUpload): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentsFileUpload);
    return this.http
      .post<IPaymentsFileUpload>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(paymentsFileUpload: IPaymentsFileUpload): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentsFileUpload);
    return this.http
      .put<IPaymentsFileUpload>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPaymentsFileUpload>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPaymentsFileUpload[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPaymentsFileUpload[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(paymentsFileUpload: IPaymentsFileUpload): IPaymentsFileUpload {
    const copy: IPaymentsFileUpload = Object.assign({}, paymentsFileUpload, {
      periodFrom:
        paymentsFileUpload.periodFrom && paymentsFileUpload.periodFrom.isValid()
          ? paymentsFileUpload.periodFrom.format(DATE_FORMAT)
          : undefined,
      periodTo:
        paymentsFileUpload.periodTo && paymentsFileUpload.periodTo.isValid() ? paymentsFileUpload.periodTo.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.periodFrom = res.body.periodFrom ? moment(res.body.periodFrom) : undefined;
      res.body.periodTo = res.body.periodTo ? moment(res.body.periodTo) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((paymentsFileUpload: IPaymentsFileUpload) => {
        paymentsFileUpload.periodFrom = paymentsFileUpload.periodFrom ? moment(paymentsFileUpload.periodFrom) : undefined;
        paymentsFileUpload.periodTo = paymentsFileUpload.periodTo ? moment(paymentsFileUpload.periodTo) : undefined;
      });
    }
    return res;
  }
}
