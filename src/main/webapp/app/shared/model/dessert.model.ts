import { IRestaurant } from 'app/shared/model/restaurant.model';

export interface IDessert {
  id?: number;
  dessertID?: string;
  description?: string;
  price?: number;
  restaurantName?: IRestaurant;
}

export class Dessert implements IDessert {
  constructor(
    public id?: number,
    public dessertID?: string,
    public description?: string,
    public price?: number,
    public restaurantName?: IRestaurant
  ) {}
}
