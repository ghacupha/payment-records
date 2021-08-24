import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPaymentsMessageToken } from 'app/shared/model/payments/payments-message-token.model';

type EntityResponseType = HttpResponse<IPaymentsMessageToken>;
type EntityArrayResponseType = HttpResponse<IPaymentsMessageToken[]>;

@Injectable({ providedIn: 'root' })
export class PaymentsMessageTokenService {
  public resourceUrl = SERVER_API_URL + 'services/paymentrecords/api/payments-message-tokens';

  constructor(protected http: HttpClient) {}

  create(paymentsMessageToken: IPaymentsMessageToken): Observable<EntityResponseType> {
    return this.http.post<IPaymentsMessageToken>(this.resourceUrl, paymentsMessageToken, { observe: 'response' });
  }

  update(paymentsMessageToken: IPaymentsMessageToken): Observable<EntityResponseType> {
    return this.http.put<IPaymentsMessageToken>(this.resourceUrl, paymentsMessageToken, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentsMessageToken>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentsMessageToken[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
