import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlate } from 'app/shared/model/plate.model';
import { PlateService } from './plate.service';

@Component({
  selector: 'jhi-plate-delete-dialog',
  templateUrl: './plate-delete-dialog.component.html'
})
export class PlateDeleteDialogComponent {
  plate: IPlate;

  constructor(protected plateService: PlateService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.plateService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'plateListModification',
        content: 'Deleted an plate'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-plate-delete-popup',
  template: ''
})
export class PlateDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ plate }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PlateDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.plate = plate;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/plate', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/plate', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
