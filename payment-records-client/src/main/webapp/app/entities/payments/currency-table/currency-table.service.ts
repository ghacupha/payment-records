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

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ICurrencyTable } from 'app/shared/model/payments/currency-table.model';

type EntityResponseType = HttpResponse<ICurrencyTable>;
type EntityArrayResponseType = HttpResponse<ICurrencyTable[]>;

@Injectable({ providedIn: 'root' })
export class CurrencyTableService {
  public resourceUrl = SERVER_API_URL + 'api/currency-tables';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/currency-tables';

  constructor(protected http: HttpClient) {}

  create(currencyTable: ICurrencyTable): Observable<EntityResponseType> {
    return this.http.post<ICurrencyTable>(this.resourceUrl, currencyTable, { observe: 'response' });
  }

  update(currencyTable: ICurrencyTable): Observable<EntityResponseType> {
    return this.http.put<ICurrencyTable>(this.resourceUrl, currencyTable, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICurrencyTable>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICurrencyTable[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICurrencyTable[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
