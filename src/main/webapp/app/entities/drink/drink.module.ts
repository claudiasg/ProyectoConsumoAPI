import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ProyectoAPiRestSharedModule } from 'app/shared';
import {
  DrinkComponent,
  DrinkDetailComponent,
  DrinkUpdateComponent,
  DrinkDeletePopupComponent,
  DrinkDeleteDialogComponent,
  drinkRoute,
  drinkPopupRoute
} from './';

const ENTITY_STATES = [...drinkRoute, ...drinkPopupRoute];

@NgModule({
  imports: [ProyectoAPiRestSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [DrinkComponent, DrinkDetailComponent, DrinkUpdateComponent, DrinkDeleteDialogComponent, DrinkDeletePopupComponent],
  entryComponents: [DrinkComponent, DrinkUpdateComponent, DrinkDeleteDialogComponent, DrinkDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProyectoAPiRestDrinkModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
