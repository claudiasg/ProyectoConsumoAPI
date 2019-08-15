import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDrink, Drink } from 'app/shared/model/drink.model';
import { DrinkService } from './drink.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant';

@Component({
  selector: 'jhi-drink-update',
  templateUrl: './drink-update.component.html'
})
export class DrinkUpdateComponent implements OnInit {
  isSaving: boolean;

  restaurants: IRestaurant[];

  editForm = this.fb.group({
    id: [],
    drinkID: [null, [Validators.required]],
    description: [],
    price: [],
    restaurantName: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected drinkService: DrinkService,
    protected restaurantService: RestaurantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ drink }) => {
      this.updateForm(drink);
    });
    this.restaurantService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRestaurant[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRestaurant[]>) => response.body)
      )
      .subscribe((res: IRestaurant[]) => (this.restaurants = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(drink: IDrink) {
    this.editForm.patchValue({
      id: drink.id,
      drinkID: drink.drinkID,
      description: drink.description,
      price: drink.price,
      restaurantName: drink.restaurantName
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const drink = this.createFromForm();
    if (drink.id !== undefined) {
      this.subscribeToSaveResponse(this.drinkService.update(this.drink.id,drink));
    } else {
      this.subscribeToSaveResponse(this.drinkService.create(drink));
    }
  }

  private createFromForm(): IDrink {
    return {
      ...new Drink(),
      id: this.editForm.get(['id']).value,
      drinkID: this.editForm.get(['drinkID']).value,
      description: this.editForm.get(['description']).value,
      price: this.editForm.get(['price']).value,
      restaurantName: this.editForm.get(['restaurantName']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDrink>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackRestaurantById(index: number, item: IRestaurant) {
    return item.id;
  }
}
