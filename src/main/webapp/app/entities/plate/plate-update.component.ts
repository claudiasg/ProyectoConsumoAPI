import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPlate, Plate } from 'app/shared/model/plate.model';
import { PlateService } from './plate.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant';

@Component({
  selector: 'jhi-plate-update',
  templateUrl: './plate-update.component.html'
})
export class PlateUpdateComponent implements OnInit {
  isSaving: boolean;

  restaurants: IRestaurant[];

  editForm = this.fb.group({
    id: [],
    plateID: [null, [Validators.required]],
    description: [],
    price: [],
    restaurantName: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected plateService: PlateService,
    protected restaurantService: RestaurantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ plate }) => {
      this.updateForm(plate);
    });
    this.restaurantService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRestaurant[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRestaurant[]>) => response.body)
      )
      .subscribe((res: IRestaurant[]) => (this.restaurants = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(plate: IPlate) {
    this.editForm.patchValue({
      id: plate.id,
      plateID: plate.plateID,
      description: plate.description,
      price: plate.price,
      restaurantName: plate.restaurantName
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const plate = this.createFromForm();
    if (plate.id !== undefined) {
      this.subscribeToSaveResponse(this.plateService.update(this.plate.id,plate));
    } else {
      this.subscribeToSaveResponse(this.plateService.create(plate));
    }
  }

  private createFromForm(): IPlate {
    return {
      ...new Plate(),
      id: this.editForm.get(['id']).value,
      plateID: this.editForm.get(['plateID']).value,
      description: this.editForm.get(['description']).value,
      price: this.editForm.get(['price']).value,
      restaurantName: this.editForm.get(['restaurantName']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlate>>) {
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
