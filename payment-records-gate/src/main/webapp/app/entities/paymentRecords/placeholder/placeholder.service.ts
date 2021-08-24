import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IPlaceholder } from 'app/shared/model/paymentRecords/placeholder.model';

type EntityResponseType = HttpResponse<IPlaceholder>;
type EntityArrayResponseType = HttpResponse<IPlaceholder[]>;

@Injectable({ providedIn: 'root' })
export class PlaceholderService {
  public resourceUrl = SERVER_API_URL + 'services/paymentrecords/api/placeholders';
  public resourceSearchUrl = SERVER_API_URL + 'services/paymentrecords/api/_search/placeholders';

  constructor(protected http: HttpClient) {}

  create(placeholder: IPlaceholder): Observable<EntityResponseType> {
    return this.http.post<IPlaceholder>(this.resourceUrl, placeholder, { observe: 'response' });
  }

  update(placeholder: IPlaceholder): Observable<EntityResponseType> {
    return this.http.put<IPlaceholder>(this.resourceUrl, placeholder, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlaceholder>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlaceholder[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlaceholder[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
