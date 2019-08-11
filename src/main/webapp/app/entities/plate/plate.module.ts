import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ProyectoAPiRestSharedModule } from 'app/shared';
import {
  PlateComponent,
  PlateDetailComponent,
  PlateUpdateComponent,
  PlateDeletePopupComponent,
  PlateDeleteDialogComponent,
  plateRoute,
  platePopupRoute
} from './';

const ENTITY_STATES = [...plateRoute, ...platePopupRoute];

@NgModule({
  imports: [ProyectoAPiRestSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PlateComponent, PlateDetailComponent, PlateUpdateComponent, PlateDeleteDialogComponent, PlateDeletePopupComponent],
  entryComponents: [PlateComponent, PlateUpdateComponent, PlateDeleteDialogComponent, PlateDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProyectoAPiRestPlateModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
