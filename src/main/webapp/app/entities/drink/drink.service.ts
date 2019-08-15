import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDrink } from 'app/shared/model/drink.model';

type EntityResponseType = HttpResponse<IDrink>;
type EntityArrayResponseType = HttpResponse<IDrink[]>;

@Injectable({ providedIn: 'root' })
export class DrinkService {
  public resourceUrl = SERVER_API_URL + 'api/drinks';

  constructor(protected http: HttpClient) {}

  create(drink: IDrink): Observable<EntityResponseType> {
    return this.http.post<IDrink>(this.resourceUrl, drink, { observe: 'response' });
  }

  update(id:number,drink: IDrink): Observable<EntityResponseType> {
    return this.http.put<IDrink>(`${this.resourceUrl}/$id`, drink, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDrink>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDrink[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
