import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Plate } from 'app/shared/model/plate.model';
import { PlateService } from './plate.service';
import { PlateComponent } from './plate.component';
import { PlateDetailComponent } from './plate-detail.component';
import { PlateUpdateComponent } from './plate-update.component';
import { PlateDeletePopupComponent } from './plate-delete-dialog.component';
import { IPlate } from 'app/shared/model/plate.model';

@Injectable({ providedIn: 'root' })
export class PlateResolve implements Resolve<IPlate> {
  constructor(private service: PlateService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPlate> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Plate>) => response.ok),
        map((plate: HttpResponse<Plate>) => plate.body)
      );
    }
    return of(new Plate());
  }
}

export const plateRoute: Routes = [
  {
    path: '',
    component: PlateComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'proyectoAPiRestApp.plate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PlateDetailComponent,
    resolve: {
      plate: PlateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'proyectoAPiRestApp.plate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PlateUpdateComponent,
    resolve: {
      plate: PlateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'proyectoAPiRestApp.plate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PlateUpdateComponent,
    resolve: {
      plate: PlateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'proyectoAPiRestApp.plate.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const platePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PlateDeletePopupComponent,
    resolve: {
      plate: PlateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'proyectoAPiRestApp.plate.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
