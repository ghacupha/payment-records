import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IPaymentsFileType } from 'app/shared/model/payments/payments-file-type.model';

type EntityResponseType = HttpResponse<IPaymentsFileType>;
type EntityArrayResponseType = HttpResponse<IPaymentsFileType[]>;

@Injectable({ providedIn: 'root' })
export class PaymentsFileTypeService {
  public resourceUrl = SERVER_API_URL + 'services/paymentrecords/api/payments-file-types';
  public resourceSearchUrl = SERVER_API_URL + 'services/paymentrecords/api/_search/payments-file-types';

  constructor(protected http: HttpClient) {}

  create(paymentsFileType: IPaymentsFileType): Observable<EntityResponseType> {
    return this.http.post<IPaymentsFileType>(this.resourceUrl, paymentsFileType, { observe: 'response' });
  }

  update(paymentsFileType: IPaymentsFileType): Observable<EntityResponseType> {
    return this.http.put<IPaymentsFileType>(this.resourceUrl, paymentsFileType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentsFileType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentsFileType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentsFileType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
