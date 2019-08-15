import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDessert, Dessert } from 'app/shared/model/dessert.model';
import { DessertService } from './dessert.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant';

@Component({
  selector: 'jhi-dessert-update',
  templateUrl: './dessert-update.component.html'
})
export class DessertUpdateComponent implements OnInit {
  isSaving: boolean;

  restaurants: IRestaurant[];

  editForm = this.fb.group({
    id: [],
    dessertID: [null, [Validators.required]],
    description: [],
    price: [],
    restaurantName: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected dessertService: DessertService,
    protected restaurantService: RestaurantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dessert }) => {
      this.updateForm(dessert);
    });
    this.restaurantService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRestaurant[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRestaurant[]>) => response.body)
      )
      .subscribe((res: IRestaurant[]) => (this.restaurants = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(dessert: IDessert) {
    this.editForm.patchValue({
      id: dessert.id,
      dessertID: dessert.dessertID,
      description: dessert.description,
      price: dessert.price,
      restaurantName: dessert.restaurantName
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const dessert = this.createFromForm();
    if (dessert.id !== undefined) {
      this.subscribeToSaveResponse(this.dessertService.update(this.dessert.id,dessert));
    } else {
      this.subscribeToSaveResponse(this.dessertService.create(dessert));
    }
  }

  private createFromForm(): IDessert {
    return {
      ...new Dessert(),
      id: this.editForm.get(['id']).value,
      dessertID: this.editForm.get(['dessertID']).value,
      description: this.editForm.get(['description']).value,
      price: this.editForm.get(['price']).value,
      restaurantName: this.editForm.get(['restaurantName']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDessert>>) {
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
