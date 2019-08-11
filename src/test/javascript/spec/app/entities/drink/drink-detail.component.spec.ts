/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProyectoAPiRestTestModule } from '../../../test.module';
import { DrinkDetailComponent } from 'app/entities/drink/drink-detail.component';
import { Drink } from 'app/shared/model/drink.model';

describe('Component Tests', () => {
  describe('Drink Management Detail Component', () => {
    let comp: DrinkDetailComponent;
    let fixture: ComponentFixture<DrinkDetailComponent>;
    const route = ({ data: of({ drink: new Drink(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProyectoAPiRestTestModule],
        declarations: [DrinkDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DrinkDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DrinkDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.drink).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
