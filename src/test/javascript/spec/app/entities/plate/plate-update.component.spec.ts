/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ProyectoAPiRestTestModule } from '../../../test.module';
import { PlateUpdateComponent } from 'app/entities/plate/plate-update.component';
import { PlateService } from 'app/entities/plate/plate.service';
import { Plate } from 'app/shared/model/plate.model';

describe('Component Tests', () => {
  describe('Plate Management Update Component', () => {
    let comp: PlateUpdateComponent;
    let fixture: ComponentFixture<PlateUpdateComponent>;
    let service: PlateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProyectoAPiRestTestModule],
        declarations: [PlateUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PlateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Plate(123);
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
        const entity = new Plate();
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
