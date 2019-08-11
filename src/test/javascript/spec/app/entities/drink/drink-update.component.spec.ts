/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ProyectoAPiRestTestModule } from '../../../test.module';
import { DrinkUpdateComponent } from 'app/entities/drink/drink-update.component';
import { DrinkService } from 'app/entities/drink/drink.service';
import { Drink } from 'app/shared/model/drink.model';

describe('Component Tests', () => {
  describe('Drink Management Update Component', () => {
    let comp: DrinkUpdateComponent;
    let fixture: ComponentFixture<DrinkUpdateComponent>;
    let service: DrinkService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProyectoAPiRestTestModule],
        declarations: [DrinkUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DrinkUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DrinkUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DrinkService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Drink(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Drink();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
