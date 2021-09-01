import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IPaymentLabel } from 'app/shared/model/paymentRecords/payment-label.model';

type EntityResponseType = HttpResponse<IPaymentLabel>;
type EntityArrayResponseType = HttpResponse<IPaymentLabel[]>;

@Injectable({ providedIn: 'root' })
export class PaymentLabelService {
  public resourceUrl = SERVER_API_URL + 'services/paymentrecords/api/payment-labels';
  public resourceSearchUrl = SERVER_API_URL + 'services/paymentrecords/api/_search/payment-labels';

  constructor(protected http: HttpClient) {}

  create(paymentLabel: IPaymentLabel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentLabel);
    return this.http
      .post<IPaymentLabel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(paymentLabel: IPaymentLabel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentLabel);
    return this.http
      .put<IPaymentLabel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPaymentLabel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPaymentLabel[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPaymentLabel[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(paymentLabel: IPaymentLabel): IPaymentLabel {
    const copy: IPaymentLabel = Object.assign({}, paymentLabel, {
      schedule: paymentLabel.schedule && paymentLabel.schedule.isValid() ? paymentLabel.schedule.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.schedule = res.body.schedule ? moment(res.body.schedule) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((paymentLabel: IPaymentLabel) => {
        paymentLabel.schedule = paymentLabel.schedule ? moment(paymentLabel.schedule) : undefined;
      });
    }
    return res;
  }
}
