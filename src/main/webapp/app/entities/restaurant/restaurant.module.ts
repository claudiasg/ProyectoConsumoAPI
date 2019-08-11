import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ProyectoAPiRestSharedModule } from 'app/shared';
import {
  RestaurantComponent,
  RestaurantDetailComponent,
  RestaurantUpdateComponent,
  RestaurantDeletePopupComponent,
  RestaurantDeleteDialogComponent,
  restaurantRoute,
  restaurantPopupRoute
} from './';

const ENTITY_STATES = [...restaurantRoute, ...restaurantPopupRoute];

@NgModule({
  imports: [ProyectoAPiRestSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RestaurantComponent,
    RestaurantDetailComponent,
    RestaurantUpdateComponent,
    RestaurantDeleteDialogComponent,
    RestaurantDeletePopupComponent
  ],
  entryComponents: [RestaurantComponent, RestaurantUpdateComponent, RestaurantDeleteDialogComponent, RestaurantDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProyectoAPiRestRestaurantModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
