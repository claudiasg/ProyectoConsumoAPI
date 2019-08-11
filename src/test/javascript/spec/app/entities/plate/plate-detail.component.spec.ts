/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProyectoAPiRestTestModule } from '../../../test.module';
import { PlateDetailComponent } from 'app/entities/plate/plate-detail.component';
import { Plate } from 'app/shared/model/plate.model';

describe('Component Tests', () => {
  describe('Plate Management Detail Component', () => {
    let comp: PlateDetailComponent;
    let fixture: ComponentFixture<PlateDetailComponent>;
    const route = ({ data: of({ plate: new Plate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProyectoAPiRestTestModule],
        declarations: [PlateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PlateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.plate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
