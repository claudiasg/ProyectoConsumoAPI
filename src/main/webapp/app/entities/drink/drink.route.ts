import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Drink } from 'app/shared/model/drink.model';
import { DrinkService } from './drink.service';
import { DrinkComponent } from './drink.component';
import { DrinkDetailComponent } from './drink-detail.component';
import { DrinkUpdateComponent } from './drink-update.component';
import { DrinkDeletePopupComponent } from './drink-delete-dialog.component';
import { IDrink } from 'app/shared/model/drink.model';

@Injectable({ providedIn: 'root' })
export class DrinkResolve implements Resolve<IDrink> {
  constructor(private service: DrinkService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDrink> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Drink>) => response.ok),
        map((drink: HttpResponse<Drink>) => drink.body)
      );
    }
    return of(new Drink());
  }
}

export const drinkRoute: Routes = [
  {
    path: '',
    component: DrinkComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'proyectoAPiRestApp.drink.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DrinkDetailComponent,
    resolve: {
      drink: DrinkResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'proyectoAPiRestApp.drink.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DrinkUpdateComponent,
    resolve: {
      drink: DrinkResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'proyectoAPiRestApp.drink.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DrinkUpdateComponent,
    resolve: {
      drink: DrinkResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'proyectoAPiRestApp.drink.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const drinkPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DrinkDeletePopupComponent,
    resolve: {
      drink: DrinkResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'proyectoAPiRestApp.drink.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
