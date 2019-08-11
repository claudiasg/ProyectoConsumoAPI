import { IRestaurant } from 'app/shared/model/restaurant.model';

export interface IPlate {
  id?: number;
  plateID?: string;
  description?: string;
  price?: number;
  restaurantName?: IRestaurant;
}

export class Plate implements IPlate {
  constructor(
    public id?: number,
    public plateID?: string,
    public description?: string,
    public price?: number,
    public restaurantName?: IRestaurant
  ) {}
}
