import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ProyectoAPiRestSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [ProyectoAPiRestSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [ProyectoAPiRestSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProyectoAPiRestSharedModule {
  static forRoot() {
    return {
      ngModule: ProyectoAPiRestSharedModule
    };
  }
}
