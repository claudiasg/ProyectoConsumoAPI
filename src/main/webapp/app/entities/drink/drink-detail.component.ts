import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDrink } from 'app/shared/model/drink.model';

@Component({
  selector: 'jhi-drink-detail',
  templateUrl: './drink-detail.component.html'
})
export class DrinkDetailComponent implements OnInit {
  drink: IDrink;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ drink }) => {
      this.drink = drink;
    });
  }

  previousState() {
    window.history.back();
  }
}
