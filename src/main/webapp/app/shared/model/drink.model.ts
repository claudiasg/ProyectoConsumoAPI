import { IRestaurant } from 'app/shared/model/restaurant.model';

export interface IDrink {
  id?: number;
  drinkID?: string;
  description?: string;
  price?: number;
  restaurantName?: IRestaurant;
}

export class Drink implements IDrink {
  constructor(
    public id?: number,
    public drinkID?: string,
    public description?: string,
    public price?: number,
    public restaurantName?: IRestaurant
  ) {}
}
