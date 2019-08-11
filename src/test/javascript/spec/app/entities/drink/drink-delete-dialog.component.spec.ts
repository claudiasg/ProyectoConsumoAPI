/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProyectoAPiRestTestModule } from '../../../test.module';
import { DrinkDeleteDialogComponent } from 'app/entities/drink/drink-delete-dialog.component';
import { DrinkService } from 'app/entities/drink/drink.service';

describe('Component Tests', () => {
  describe('Drink Management Delete Component', () => {
    let comp: DrinkDeleteDialogComponent;
    let fixture: ComponentFixture<DrinkDeleteDialogComponent>;
    let service: DrinkService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProyectoAPiRestTestModule],
        declarations: [DrinkDeleteDialogComponent]
      })
        .overrideTemplate(DrinkDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DrinkDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DrinkService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
