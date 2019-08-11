import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDrink } from 'app/shared/model/drink.model';
import { DrinkService } from './drink.service';

@Component({
  selector: 'jhi-drink-delete-dialog',
  templateUrl: './drink-delete-dialog.component.html'
})
export class DrinkDeleteDialogComponent {
  drink: IDrink;

  constructor(protected drinkService: DrinkService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.drinkService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'drinkListModification',
        content: 'Deleted an drink'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-drink-delete-popup',
  template: ''
})
export class DrinkDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ drink }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DrinkDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.drink = drink;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/drink', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/drink', { outlets: { popup: null } }]);
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
