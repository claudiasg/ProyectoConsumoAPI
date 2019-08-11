import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlate } from 'app/shared/model/plate.model';

@Component({
  selector: 'jhi-plate-detail',
  templateUrl: './plate-detail.component.html'
})
export class PlateDetailComponent implements OnInit {
  plate: IPlate;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ plate }) => {
      this.plate = plate;
    });
  }

  previousState() {
    window.history.back();
  }
}
