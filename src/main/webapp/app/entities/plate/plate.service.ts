import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPlate } from 'app/shared/model/plate.model';

type EntityResponseType = HttpResponse<IPlate>;
type EntityArrayResponseType = HttpResponse<IPlate[]>;

@Injectable({ providedIn: 'root' })
export class PlateService {
  public resourceUrl = SERVER_API_URL + 'api/plates';

  constructor(protected http: HttpClient) {}

  create(plate: IPlate): Observable<EntityResponseType> {
    return this.http.post<IPlate>(this.resourceUrl, plate, { observe: 'response' });
  }

  update(plate: IPlate): Observable<EntityResponseType> {
    return this.http.put<IPlate>(this.resourceUrl, plate, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
