import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'department',
        loadChildren: () => import('./department/department.module').then(m => m.ProyectoAPiRestDepartmentModule)
      },
      {
        path: 'plate',
        loadChildren: () => import('./plate/plate.module').then(m => m.ProyectoAPiRestPlateModule)
      },
      {
        path: 'drink',
        loadChildren: () => import('./drink/drink.module').then(m => m.ProyectoAPiRestDrinkModule)
      },
      {
        path: 'dessert',
        loadChildren: () => import('./dessert/dessert.module').then(m => m.ProyectoAPiRestDessertModule)
      },
      {
        path: 'restaurant',
        loadChildren: () => import('./restaurant/restaurant.module').then(m => m.ProyectoAPiRestRestaurantModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProyectoAPiRestEntityModule {}
